/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(generator = "visit_generator")
    @SequenceGenerator(
        name = "visit_generator",
        sequenceName = "visits_id_seq",
        initialValue = 100,
        allocationSize = 1
    )
    private Integer id;

    @Column(name = "visit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime visitDate;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @Column(name = "is_finished")
    private boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

    public Visit() {

    }

    public Visit(Pet pet, Vet vet, LocalDateTime visitDate, String description) {
        this.pet = pet;
        this.vet = vet;
        this.visitDate = visitDate;
        this.description = description;
    }
}
