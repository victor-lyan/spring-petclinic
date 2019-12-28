package org.springframework.samples.petclinic.dto;

import org.springframework.samples.petclinic.model.PetType;

import javax.validation.constraints.NotBlank;

public class PetAjaxRequest {

    private int petId;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "birthdate cannot be blank")
    private String birthDate;

    private int typeId;

    PetAjaxRequest() {

    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
