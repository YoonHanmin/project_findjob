package com.project.findjob.repository;

import com.project.findjob.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    Resume findByUserid(String userid);
    boolean existsByUserid(String userid);
    void deleteByUserid(String userid);
}
