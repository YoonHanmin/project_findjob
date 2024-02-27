package com.project.findjob.repository;

import com.project.findjob.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {

    Optional<Store> findById(Long id);
    Store findByUserid(String userid);
    boolean existsByUserid(String userid);
    void deleteByUserid(String userid);
    Store findByArea2(String area2);
}
