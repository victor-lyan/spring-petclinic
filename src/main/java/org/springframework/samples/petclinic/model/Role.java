package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(generator = "role_generator")
    @SequenceGenerator(
        name = "role_generator",
        sequenceName = "roles_id_seq",
        initialValue = 100,
        allocationSize = 1
    )
    private Integer id;

    private String name;
}
