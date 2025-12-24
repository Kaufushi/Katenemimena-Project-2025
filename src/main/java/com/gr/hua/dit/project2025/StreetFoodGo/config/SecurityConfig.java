package com.gr.hua.dit.project2025.StreetFoodGo.config;

import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain appChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLIC PAGES
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/restaurants",
                                "/restaurants/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()


                        .requestMatchers(
                                "/restaurants/new",
                                "/restaurants/save",
                                "/restaurants/*/edit",
                                "/restaurants/*/delete"
                        ).hasRole("OWNER")


                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

}
