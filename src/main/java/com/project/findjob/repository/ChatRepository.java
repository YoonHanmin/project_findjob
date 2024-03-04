package com.project.findjob.repository;

import com.project.findjob.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    List<Chat> findByToNameOrderByTimeDesc(String toName);
    List<Chat> findByFromNameOrderByTimeDesc(String fromName);
    List<Chat> findByToNameAndFromNameOrToNameAndFromNameOrderByIdAsc(String toName1, String fromName1, String toName2, String fromName2);

    List<Chat> findByFromName(String fromName);
    Chat findTopByChatListIdOrderByIdDesc(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Chat c SET c.readchat = true WHERE c.fromName = :fromName AND c.toName = :toName")
    void markAsReadchatByFromNameAndToName(@Param("toName") String toName, @Param("fromName") String fromName);
}
