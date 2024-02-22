package com.project.findjob.repository;

import com.project.findjob.model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployRepository extends JpaRepository<Employment,Long> {

}
