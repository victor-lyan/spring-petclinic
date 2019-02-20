package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.repo.VetRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class SpecialtyFormatter implements Formatter<Specialty> {

    private final VetRepository vetRepository;

    @Autowired
    public SpecialtyFormatter(VetRepository vets) {
        this.vetRepository = vets;
    }

    @Override
    public String print(Specialty specialty, Locale locale) {
        return specialty.getName();
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        Collection<Specialty> allSpecialties = this.vetRepository.getAllSpecialties();
        for (Specialty specialty : allSpecialties) {
            if (specialty.getName().equals(text)) {
                return specialty;
            }
        }
        throw new ParseException("specialty not found: " + text, 0);
    }

}
