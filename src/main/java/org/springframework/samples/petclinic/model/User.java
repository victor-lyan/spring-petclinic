package org.springframework.samples.petclinic.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
        name = "user_generator",
        sequenceName = "users_id_seq",
        initialValue = 100
    )
    protected Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "*Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "*Please provide your last name")
    private String lastName;

    @Email(message = "*Please provide a valid Email")
    @NotBlank(message = "*Please provide an email")
    private String email;

    @NotBlank(message = "*Please provide a password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "activation_code")
    private String activationCode;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modified;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
