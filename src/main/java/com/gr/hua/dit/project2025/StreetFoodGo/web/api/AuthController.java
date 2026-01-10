package com.gr.hua.dit.project2025.StreetFoodGo.web.api;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.AuthResponseDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.LoginRequestDto;
import com.gr.hua.dit.project2025.StreetFoodGo.security.JwtService;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(PersonDetailsService personDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.personDetailsService = personDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        System.out.println("====== API LOGIN CALLED ======");
        System.out.println("Email received: " + request.email());
        PersonDetails userDetails = (PersonDetails) personDetailsService.loadUserByUsername(request.email());

        // Check password manually
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Issue JWT token
        String token = jwtService.issue(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .collect(Collectors.toList())
        );

        AuthResponseDto response = new AuthResponseDto(
                token,
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority()
        );

        return ResponseEntity.ok(response);
    }
}