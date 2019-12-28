/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.dto.AjaxResponse;
import org.springframework.samples.petclinic.dto.PetAjaxRequest;
import org.springframework.samples.petclinic.dto.PetAjaxResponse;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repo.OwnerRepository;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repo.PetRepository;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.PetValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/pets")
class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetRepository pets;
    private final OwnerRepository owners;
    private PetService petService;
    private OwnerService ownerService;

    public PetController(PetRepository pets, OwnerRepository owners, PetService petService, OwnerService ownerService) {
        this.pets = pets;
        this.owners = owners;
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petService.findPetTypes();
    }

    /*@InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }*/

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("load-pet-modal")
    public String loadPetModal(
        @RequestParam(required = false) Integer petId,
        Model model,
        Principal principal
    ) {
        Pet pet = new Pet();
        Owner currentOwner = ownerService.findByEmail(principal.getName());
        if (petId != null) {
            pet = petService.findById(petId);
            if (pet == null) {
                model.addAttribute("message", "Pet not found");
                return "error";
            }
        }

        if (currentOwner == null) {
            model.addAttribute("message", "Current user is not an owner");
            return "error";
        }

        model.addAttribute("pet", pet);
        return "pets/pet-modal";
    }

    @PostMapping("save-pet")
    public @ResponseBody ResponseEntity<?> savePet(
        @RequestBody @Valid PetAjaxRequest petAjax,
        BindingResult result,
        Principal principal
    ) {
        AjaxResponse<PetAjaxResponse> response = new AjaxResponse<>();
        if (result.hasErrors()) {
            response.setValid(false);
            response.addMessage(result.getAllErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.joining(", ")));
        }

        // parse birthdate
        LocalDate ld = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ld = LocalDate.parse(petAjax.getBirthDate(), dtf);
        } catch (DateTimeParseException ex) {
            response.setValid(false);
            response.addMessage("Invalid birthdate format");
        }

        if (response.isValid()) {
            Owner currentOwner = ownerService.findByEmail(principal.getName());
            Pet savedPet = petService.savePet(petAjax, ld, currentOwner);

            response.setValid(true);
            response.setSavedElem(new PetAjaxResponse(savedPet));
            response.addMessage("Your pet has been saved successfully");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("delete-pet/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable int petId, Principal principal) {
        AjaxResponse<PetAjaxResponse> response = new AjaxResponse<>();
        Owner currentOwner = ownerService.findByEmail(principal.getName());
        Pet pet = petService.findByIdAndOwnerId(petId, currentOwner.getId());

        if (pet == null) {
            response.setValid(false);
            response.addMessage("Pet not found");
        } else {
            petService.deletePet(pet);
            response.setValid(true);
            response.addMessage("Your pet has been removed successfully");
            response.setSavedElem(new PetAjaxResponse(pet));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, ModelMap model) {
        Pet pet = new Pet();
        owner.addPet(pet);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
        if (StringUtils.hasLength(pet.getName()) && owner.getPet(pet.getName()) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.addPet(pet);
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            this.pets.save(pet);
            return "redirect:/owners/{ownerId}";
        }
    }
}
