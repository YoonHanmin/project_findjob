package com.project.findjob.repository;

import com.project.findjob.model.Employment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployRepository extends JpaRepository<Employment,Long> {
    List<Employment> findByOwnerid(String ownerid);
    Page<Employment> findByArea1ContainingOrArea2ContainingOrJobContainingOrTimeContaining(String area1, String area2,String job,String time, Pageable pageable);


}
