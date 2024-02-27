package com.project.findjob.repository;

import com.project.findjob.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    Resume findByUserid(String userid);
    boolean existsByUserid(String userid);
    void deleteByUserid(String userid);
    List<Resume> findByLoc(String loc);
}
