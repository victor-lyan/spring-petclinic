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
import org.springframework.samples.petclinic.dto.VisitAjaxRequest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repo.PetRepository;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repo.VisitRepository;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@Controller
class VisitController {

    private final VisitRepository visits;
    private final PetRepository pets;
    private VisitService visitService;
    private PetService petService;
    private VetService vetService;
    private OwnerService ownerService;


    public VisitController(
        VisitRepository visits,
        PetRepository pets,
        VisitService visitService,
        PetService petService,
        VetService vetService, OwnerService ownerService) {
        this.visits = visits;
        this.pets = pets;
        this.visitService = visitService;
        this.petService = petService;
        this.vetService = vetService;
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
    /*@ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
        Pet pet = this.pets.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }*/

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            this.visits.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping("/vets/{vetId}/load-visit-modal")
    public String loadVisitModal(
        @PathVariable int vetId,
        Model model,
        Principal principal
    ) {
        Vet vet = vetService.findById(vetId);
        Owner currentOwner = ownerService.findByEmail(principal.getName());
        if (vet == null) {
            model.addAttribute("message", "Veterinarian not found");
            return "error";
        }
        if (currentOwner == null) {
            model.addAttribute("message", "Current user is not an owner");
            return "error";
        }

        model.addAttribute("vet", vet);
        model.addAttribute("pets", currentOwner.getPets());
        model.addAttribute("visit", new Visit());
        return "vets/visit-modal";
    }

    @PostMapping("/vets/{vetId}/save-visit")
    public @ResponseBody ResponseEntity<?> saveVisit(
        @PathVariable int vetId,
        @Valid @RequestBody VisitAjaxRequest visitRequest,
        BindingResult result
    ) {
        AjaxResponse response = new AjaxResponse();
        if (result.hasErrors()) {
            response.setValid(false);
            response.addMessage(result.getAllErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
        }

        // parse date
        LocalDateTime ldt = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            ldt = LocalDateTime.parse(visitRequest.getVisitDate(), dtf);
        } catch (DateTimeParseException e) {
            response.setValid(false);
            response.addMessage("Invalid datetime format");
        }

        if (response.isValid()) {
            Vet vet = vetService.findById(vetId);
            Pet pet = petService.findById(visitRequest.getPetId());
            if (pet != null && ldt != null && vet != null) {
                Visit visit = new Visit(pet, vet, ldt, visitRequest.getDescription());
                this.visitService.addVisit(visit);
            }

            response.setValid(true);
            response.addMessage("Your visit has been added successfully");
        }

        return ResponseEntity.ok(response);
    }

}
