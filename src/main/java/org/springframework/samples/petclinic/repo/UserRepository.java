package org.springframework.samples.petclinic.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    User findByEmail(String email);

    @Transactional(readOnly = true)
    User findByActivationCode(String code);

    @Transactional(readOnly = true)
    User findById(int id);
}
