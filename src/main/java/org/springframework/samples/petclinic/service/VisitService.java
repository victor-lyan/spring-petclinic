package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.dto.VisitDto;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repo.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<VisitDto> getVetVisits(Vet vet) {
        List<VisitDto> visitDtos = new ArrayList<>();
        List<Visit> visits = visitRepository.findByVetId(vet.getId());
        for (Visit visit : visits) {
            VisitDto visitDto = new VisitDto(visit);
            visitDtos.add(visitDto);
        }

        return visitDtos;
    }
}
