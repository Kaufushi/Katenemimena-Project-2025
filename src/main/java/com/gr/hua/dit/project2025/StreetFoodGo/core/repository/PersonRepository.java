package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByUsername(String Username);
    boolean existsByMobileNumber(String mobileNumber);

    Optional<Person> findByUsernameOrEmail(String username,String email);
}
