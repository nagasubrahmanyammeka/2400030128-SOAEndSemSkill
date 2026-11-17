package com.klu.springmvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.klu.springmvc.model.Tourist;
import com.klu.springmvc.repo.TouristRepository;

@Service
public class TouristService {

    @Autowired
    private TouristRepository repository;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    // REGISTER AND GENERATE JWT
    public String register(Tourist user) {

        if (repository.findByUsername(user.getUsername()) != null) {
            return "Username Already Exists";
        }

        user.setPassword(encoder.encode(user.getPassword()));

        Tourist savedUser = repository.save(user);

        // Generate token after registration
        return jwtService.generateJWT(savedUser);
    }

    // LOGIN AND GENERATE JWT
    public String login(Tourist user) {

        Tourist existingUser =
                repository.findByUsername(user.getUsername());

        if (existingUser != null &&
                encoder.matches(user.getPassword(),
                        existingUser.getPassword())) {

            return jwtService.generateJWT(existingUser);
        }

        return "Invalid Username or Password";
    }

    // GET ALL TOURISTS WITH JWT VALIDATION
    public List<Tourist> getAllTourists(String token) {

        Map<String, Object> result =
                jwtService.validateJWT(token);

        return repository.findAll();
    }
}