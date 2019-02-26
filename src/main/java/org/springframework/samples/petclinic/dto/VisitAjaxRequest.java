package org.springframework.samples.petclinic.dto;

import javax.validation.constraints.NotBlank;

public class VisitAjaxRequest {

    private String visitDate;

    private int petId;

    @NotBlank(message = "description cannot be blank")
    private String description;

    VisitAjaxRequest() {

    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
