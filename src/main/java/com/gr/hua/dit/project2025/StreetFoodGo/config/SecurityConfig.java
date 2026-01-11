package com.gr.hua.dit.project2025.StreetFoodGo.config;

import com.gr.hua.dit.project2025.StreetFoodGo.security.JwtAuthenticationFilter;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final JwtAuthenticationFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PersonDetailsService personDetailsService,
                          JwtAuthenticationFilter jwtFilter,
                          PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }

    // -----------------------
    // API / JWT security
    // -----------------------
    @Bean
    @Order(1) // API chain should have higher priority
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/notifications/**").permitAll()
                        .requestMatchers("/api/v1/owner/**").hasRole("OWNER")
                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // -----------------------
    // MVC / UI security
    // -----------------------
    @Bean
    @Order(2) // UI chain runs after API chain
    public SecurityFilterChain appChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/register",
                                "/restaurants", "/restaurants/**",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        .requestMatchers(
                                "/restaurants/new",
                                "/restaurants/save",
                                "/restaurants/*/edit",
                                "/restaurants/*/delete"
                        ).hasRole("OWNER")
                        .requestMatchers("/restaurants/*/checkout").authenticated()
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
