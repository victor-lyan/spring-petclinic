package org.springframework.samples.petclinic.dto;

import org.springframework.samples.petclinic.model.Pet;

public class PetAjaxResponse {

    private int id;
    private String name;
    private String birthDate;
    private String type;

    public PetAjaxResponse() {

    }

    public PetAjaxResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.birthDate = pet.getBirthDateString();
        this.type = pet.getType().getName();
    }

    public PetAjaxResponse(int id, String name, String birthDate, String type) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
