package com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserOfApplication, Long> {

    Optional<UserOfApplication> findByUsername(String username);
}