package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmailAddress(String emailAddress);

    Person findByEmailAddress(String emailAddress);
}