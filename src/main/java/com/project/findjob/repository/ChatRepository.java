package com.project.findjob.repository;

import com.project.findjob.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    List<Chat> findByToNameOrderByTimeDesc(String toName);
    List<Chat> findByFromNameOrderByTimeDesc(String fromName);
    List<Chat> findByToNameAndFromNameOrToNameAndFromNameOrderByIdAsc(String toName1, String fromName1, String toName2, String fromName2);

}
