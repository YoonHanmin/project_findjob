package com.project.findjob.repository;

import com.project.findjob.model.Personality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalityRepository extends JpaRepository<Personality,Long> {
    Optional<Personality> findById(Long id);
}
