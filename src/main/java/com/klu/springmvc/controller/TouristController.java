package com.klu.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.springmvc.model.Tourist;
import com.klu.springmvc.service.TouristService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/tourists")
public class TouristController {

    @Autowired
    private TouristService service;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody Tourist tourist) {
        return service.register(tourist);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Tourist tourist) {
        return service.login(tourist);
    }

    // GET ALL TOURISTS WITH JWT VALIDATION
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAllTourists(
            @Parameter(hidden = true)
            @RequestHeader(value = "Authorization", required = false)
            String authHeader) {

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            String token = authHeader.substring(7);

            List<Tourist> tourists = service.getAllTourists(token);

            return ResponseEntity.ok(tourists);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or Expired JWT Token");
        }
    }
}