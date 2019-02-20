package org.springframework.samples.petclinic.controller;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RoleService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.validation.Valid;

@Controller
public class LoginController {

    private UserService userService;
    private RoleService roleService;
    private VetService vetService;
    private OwnerService ownerService;

    public LoginController(
        UserService userService,
        RoleService roleService,
        VetService vetService,
        OwnerService ownerService
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.vetService = vetService;
        this.ownerService = ownerService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login/login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        List<Role> roles = roleService.getRolesList();

        // in case if it's not a redirect from POST request, we populate the model
        if (!model.containsAttribute("owner")) {
            Owner owner = new Owner();
            model.addAttribute("owner", owner);
        }
        if (!model.containsAttribute("vet")) {
            Vet vet = new Vet();
            model.addAttribute("vet", vet);
        }
        model.addAttribute("roles", roles);
        model.addAttribute("rolesList", roleService.getRolesList());
        model.addAttribute("specialtiesList", vetService.getAllSpecialties());

        return "login/registration";
    }

    @PostMapping("/owner-registration")
    public String registerOwner(@Valid Owner owner, BindingResult result, RedirectAttributes redirectAttributes) {
        // if there are validation errors show them immediately without further processing
        if (result.hasErrors()) {
            // here we redirect to /registration page with errors included
            redirectAttributes.addFlashAttribute("owner", owner);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.owner", result);
            redirectAttributes.addFlashAttribute("ownerErrors", true);
            return "redirect:/registration";
        }

        if (!ownerService.addOwner(owner)) {
            //if there is already a user with the same email registered, return an error
            redirectAttributes.addFlashAttribute("ownerErrors", true);
            redirectAttributes.addFlashAttribute("userAlreadyExistsError", "User with the same email already exists");
            return "redirect:/registration";
        }

        return "redirect:/registration-success";
    }

    @PostMapping("/vet-registration")
    public String registerVet(@Valid Vet vet, BindingResult result, RedirectAttributes redirectAttributes) {
        //if there are validation errors show them immediately without further processing
        if (result.hasErrors()) {
            // here we redirect to /registration page with errors included
            redirectAttributes.addFlashAttribute("vet", vet);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vet", result);
            redirectAttributes.addFlashAttribute("vetErrors", true);
            return "redirect:/registration";
        }

        if (!vetService.addVet(vet)) {
            //if there is already a user with the same email registered, return an error
            redirectAttributes.addFlashAttribute("vetErrors", true);
            redirectAttributes.addFlashAttribute("userAlreadyExistsError", "User with the same email already exists");
            return "redirect:/registration";
        }

        return "redirect:/registration-success";
    }

    @GetMapping("/registration-success")
    public String showRegistrationSuccessPage() {
        return "login/registration-success";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "login/access-denied";
    }

    @PostMapping("/error")
    public String showErrorPage() {
        return "error";
    }

    @GetMapping("/activate-account/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        boolean result = userService.activateUser(code);

        if (!result) {
            model.addAttribute("message", "Activation failed");
            return "error";
        } else {
            return "login/activation-success";
        }
    }
}
