package org.springframework.samples.petclinic.dto;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Visit;

import java.time.format.DateTimeFormatter;

public class VisitDto {

    private int id;
    private String owner;
    private String pet;
    private String visitDate;
    private String description;
    private boolean isFinished;

    public VisitDto(Visit visit) {
        Owner owner = visit.getPet().getOwner();
        id = visit.getId();
        this.owner = owner.getFirstName() + " " + owner.getLastName();
        pet = visit.getPet().getName() + " - " + visit.getPet().getType().getName();
        visitDate = visit.getVisitDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        description = visit.getDescription();
        isFinished = visit.isFinished();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
