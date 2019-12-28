package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repo.RoleRepository;
import org.springframework.samples.petclinic.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {

    protected UserRepository userRepository;

    protected RoleRepository roleRepository;

    protected BCryptPasswordEncoder passwordEncoder;

    protected MailService mailService;

    public UserService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        BCryptPasswordEncoder passwordEncoder,
        MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    private void sendEmail(User user) {
        mailService.sendActivationEmail(
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getActivationCode()
        );
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    boolean checkUserExists(User user) {
        User userExists = findUserByEmail(user.getEmail());
        return userExists == null;
    }

    void prepareForActivation(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(role));

        sendEmail(user);
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);
        return true;
    }

    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    public boolean comparePassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
