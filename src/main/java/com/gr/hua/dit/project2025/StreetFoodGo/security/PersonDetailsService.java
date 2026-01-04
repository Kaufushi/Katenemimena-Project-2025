package com.gr.hua.dit.project2025.StreetFoodGo.security;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        // login με username:
        Person person = personRepository.findByUsername(usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + usernameOrEmail));

        // Login με email:
        // Person person = personRepository.findByEmailAddress(usernameOrEmail)
        //        .orElseThrow(() ->
        //                new UsernameNotFoundException("User not found: " + usernameOrEmail));

        return new PersonDetails(person);
    }
}