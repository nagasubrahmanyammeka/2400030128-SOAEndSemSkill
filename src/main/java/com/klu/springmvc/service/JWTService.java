package com.klu.springmvc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Tourist;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    String key = "ifymhvkcjfmgigoggwogowgwohgmohmifuhfiuf";

    SecretKey skey = Keys.hmacShaKeyFor(key.getBytes());

    // GENERATE JWT
    public String generateJWT(Tourist user) {

        Map<String, Object> claim = new HashMap<>();

        claim.put("id", user.getId());
        claim.put("username", user.getUsername());
        claim.put("email", user.getEmail());

        return Jwts.builder()
                .claims(claim)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(skey)
                .compact();
    }

    // VALIDATE JWT
    public Map<String, Object> validateJWT(String token) {

        Claims claim = Jwts.parser()
                .verifyWith(skey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Map<String, Object> result = new HashMap<>();

        result.put("id", claim.get("id"));
        result.put("username", claim.get("username"));
        result.put("email", claim.get("email"));

        return result;
    }
}