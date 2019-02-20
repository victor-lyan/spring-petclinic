package org.springframework.samples.petclinic.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Transactional(readOnly = true)
    Role findByName(String name);
}
