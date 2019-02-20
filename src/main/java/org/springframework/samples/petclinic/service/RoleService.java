package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.repo.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRolesList() {
        return roleRepository.findAll();
    }
}
