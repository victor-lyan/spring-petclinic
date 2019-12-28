package org.springframework.samples.petclinic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repo.OwnerRepository;
import org.springframework.samples.petclinic.repo.RoleRepository;
import org.springframework.samples.petclinic.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService extends UserService {

    private OwnerRepository ownerRepository;

    public OwnerService(
        OwnerRepository ownerRepository,
        UserRepository userRepository,
        RoleRepository roleRepository,
        BCryptPasswordEncoder passwordEncoder,
        MailService mailService
    ) {
        super(userRepository, roleRepository, passwordEncoder, mailService);
        this.ownerRepository = ownerRepository;
    }

    public boolean addOwner(Owner owner) {
        if (!checkUserExists(owner))
            return false;

        prepareForActivation(owner, "OWNER");
        ownerRepository.save(owner);

        return true;
    }

    public Page<Owner> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    public Owner findById(Integer id) {
        return ownerRepository.findById(id);
    }

    public Owner findByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    public void updateOwnerProfile(Owner updatedOwner, String email) {
        Owner currentOwner = findByEmail(email);
        currentOwner.setFirstName(updatedOwner.getFirstName());
        currentOwner.setLastName(updatedOwner.getLastName());
        currentOwner.setAddress(updatedOwner.getAddress());
        currentOwner.setCity(updatedOwner.getCity());
        currentOwner.setPhone(updatedOwner.getPhone());

        ownerRepository.save(currentOwner);
    }
}
