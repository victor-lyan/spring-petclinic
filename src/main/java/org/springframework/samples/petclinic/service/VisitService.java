package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repo.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class VisitService {

    private VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public void addVisit(Visit visit) {
        visit.setFinished(false);
        this.visitRepository.save(visit);
    }
}
