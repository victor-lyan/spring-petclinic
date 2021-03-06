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
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/vets")
class VetController extends PersonController {

    private VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping
    public String showVetList(
        Model model,
        @RequestParam Optional<Integer> page,
        @RequestParam Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Vet> vetPage = vetService.findAll(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("vetPage", vetPage);

        //for pagination buttons
        int totalPages = vetPage.getTotalPages();
        setPageNumbers(totalPages, model);
        return "vets/vetList";
    }

    @GetMapping("{vetId}")
    public String showVetPage(@PathVariable int vetId, Model model) {
        Vet vet = vetService.findById(vetId);
        if (vet == null) {
            model.addAttribute("message", "Veterinarian not found");
            return "error";
        }
        model.addAttribute("vet", vet);
        return "vets/vet-page";
    }
}
