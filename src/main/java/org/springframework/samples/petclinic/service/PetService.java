package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repo.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet findById(int id) {
        return petRepository.findById(id);
    }
}
