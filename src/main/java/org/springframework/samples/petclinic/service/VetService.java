package org.springframework.samples.petclinic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repo.RoleRepository;
import org.springframework.samples.petclinic.repo.UserRepository;
import org.springframework.samples.petclinic.repo.VetRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetService extends UserService {

    private VetRepository vetRepository;

    public VetService(
        VetRepository vetRepository,
        UserRepository userRepository,
        RoleRepository roleRepository,
        BCryptPasswordEncoder passwordEncoder,
        MailService mailService
    ) {
        super(userRepository, roleRepository, passwordEncoder, mailService);
        this.vetRepository = vetRepository;
    }

    public List<Specialty> getAllSpecialties() {
        return vetRepository.getAllSpecialties();
    }

    public boolean addVet(Vet vet) {
        if (!checkUserExists(vet))
            return false;

        prepareForActivation(vet, "VET");
        vetRepository.save(vet);

        return true;
    }

    public Page<Vet> findAll(Pageable pageable) {
        return vetRepository.findAll(pageable);
    }

    public Vet findById(int vetId) {
        return vetRepository.findById(vetId);
    }
}
