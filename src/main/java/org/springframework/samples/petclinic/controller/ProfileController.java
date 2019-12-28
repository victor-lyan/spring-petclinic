package org.springframework.samples.petclinic.controller;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.OwnerProfileValidator;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetProfileValidator;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private OwnerService ownerService;
    private VetService vetService;
    private UserService userService;
    private VisitService visitService;

    public ProfileController(
        OwnerService ownerService,
        VetService vetService,
        UserService userService,
        VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.userService = userService;
        this.visitService = visitService;
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new OwnerProfileValidator());
    }

    @InitBinder("vet")
    public void initVetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new VetProfileValidator());
    }

    /*
    ** It was @ModelAttribute method returning User object, but it seems that it overwrote validation
    * error attributes and that's why I changed it to simple private method
     */
    private User loadCurrentUser(Model model) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext()
            .getAuthentication().getName());
        model.addAttribute("isOwner", user instanceof Owner);
        model.addAttribute("isVet", user instanceof Vet);
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", user);
        }

        return user;
    }

    @GetMapping
    public String showProfileOverview(Model model) {
        loadCurrentUser(model);
        return "profile/profile-page";
    }

    @GetMapping("change-password")
    public String showChangePassword(Model model) {
        loadCurrentUser(model);
        return "profile/change-password";
    }

    @PostMapping("change-password")
    public String changePasswordSave(
        @RequestBody MultiValueMap<String, String> data,
        RedirectAttributes redirectAttributes
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oldPassword = data.get("old_password").get(0);
        String newPassword = data.get("new_password").get(0);
        String newPassword2 = data.get("new_password2").get(0);
        boolean hasError = false;

        if (oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || newPassword2.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "All fields must be filled!");
            hasError = true;
        }
        if (!newPassword.equals(newPassword2)) {
            redirectAttributes.addFlashAttribute("error", "Password confirmation is wrong!");
            hasError = true;
        }

        User user = userService.findUserByEmail(auth.getName());
        if (!userService.comparePassword(user, oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "Your old password is wrong!");
            hasError = true;
        }

        if (!hasError) {
            userService.changePassword(user, newPassword);
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                auth.getName(),
                newPassword,
                auth.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            redirectAttributes.addFlashAttribute("success", "Your password has been changed successfully!");
        }

        return "redirect:/profile/change-password";
    }

    @GetMapping("edit-profile")
    public String showEditProfile(Model model) {
        loadCurrentUser(model);
        model.addAttribute("allSpecialties", this.vetService.getAllSpecialties());
        return "profile/edit-profile";
    }

    @PostMapping("edit-profile-owner")
    public String editProfileSave(@Valid Owner owner, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", owner);
            return "redirect:/profile/edit-profile";
        }

        ownerService.updateOwnerProfile(
            owner,
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        redirectAttributes.addFlashAttribute("success", "Your profile has been updated successfully!");

        return "redirect:/profile/edit-profile";
    }

    @PostMapping("edit-profile-vet")
    public String editProfileSave(@Valid Vet vet, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", vet);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            return "redirect:/profile/edit-profile";
        }

        vetService.updateVetProfile(
            vet,
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        redirectAttributes.addFlashAttribute("success", "Your profile has been updated successfully!");

        return "redirect:/profile/edit-profile";
    }

    @GetMapping("manage-visits")
    public String showManagePets(Model model) {
        User currentUser = loadCurrentUser(model);
        model.addAttribute("visits", visitService.getVetVisits((Vet) currentUser));

        return "profile/manage-visits";
    }
}
