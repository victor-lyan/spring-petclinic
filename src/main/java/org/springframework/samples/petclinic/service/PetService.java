package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.dto.PetAjaxRequest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repo.PetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet findById(int id) {
        return petRepository.findById(id);
    }

    public List<PetType> findPetTypes() {
        return petRepository.findPetTypes();
    }

    public Pet savePet(PetAjaxRequest petAjax, LocalDate birthDate, Owner owner) {
        Pet pet;
        PetType type = petRepository.findPetTypeById(petAjax.getTypeId());

        if (petAjax.getPetId() == 0) {
            // new pet
            pet = new Pet(petAjax.getName(), birthDate, type, owner);
        } else {
            // edit pet
            pet = petRepository.findById(petAjax.getPetId());
            pet.setName(petAjax.getName());
            pet.setBirthDate(birthDate);
            pet.setType(type);
        }

        petRepository.save(pet);
        return pet;
    }

    public Pet findByIdAndOwnerId(int petId, int ownerId) {
        return petRepository.findPetByIdAndOwnerId(petId, ownerId);
    }

    public void deletePet(Pet pet) {
        petRepository.deleteById(pet.getId());
    }
}
