package com.project.findjob.repository;

import com.project.findjob.model.Employment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployRepository extends JpaRepository<Employment,Long> {
    List<Employment> findByOwneridAndStatus(String ownerid,String status);
    Page<Employment> findByArea1ContainingAndArea2ContainingAndJobAndTimeContaining(String area1, String area2,Long job,String time, Pageable pageable);
    Page<Employment> findByArea1ContainingAndArea2ContainingAndTimeContaining(String area1, String area2,String time, Pageable pageable);
    Page<Employment> findByArea1ContainingOrArea2ContainingOrJobOrTimeContaining(String area1, String area2,Long job,String time, Pageable pageable);

    Employment findByOwnerid(String ownerid);


}
