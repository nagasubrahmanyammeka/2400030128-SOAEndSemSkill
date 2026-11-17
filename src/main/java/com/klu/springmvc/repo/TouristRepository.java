package com.klu.springmvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.springmvc.model.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long> {

    Tourist findByUsername(String username);
}