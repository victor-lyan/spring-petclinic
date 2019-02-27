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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repo.OwnerRepository;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/owners")
public class OwnerController extends PersonController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerRepository owners;
    private OwnerService ownerService;


    public OwnerController(OwnerRepository clinicService, OwnerService ownerService) {
        this.owners = clinicService;
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("new")
    public String initCreationForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            this.owners.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("find")
    public String initFindForm(Owner owner, BindingResult result, Map<String, Object> model) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping
    public String processFindForm(
        Model model,
        @RequestParam Optional<Integer> page,
        @RequestParam Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Owner> ownerPage = ownerService.findAll(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ownerPage", ownerPage);

        //for pagination buttons
        int totalPages = ownerPage.getTotalPages();
        setPageNumbers(totalPages, model);

        return "owners/ownersList";
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
        Owner owner = this.owners.findById(ownerId);
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            this.owners.save(owner);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping("{ownerId}")
    public String showOwner(@PathVariable("ownerId") int ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        if (owner == null) {
            model.addAttribute("message", "Owner not found");
            return "error";
        }
        model.addAttribute("owner", owner);
        return "owners/owner-page";
    }

}
