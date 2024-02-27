package com.project.findjob.repository;

import com.project.findjob.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserid(String userid);
    boolean existsByUserid(String userid);
    @Transactional
    User findByUname(String uname);

    List<User> findByArea2(String area2);
}
