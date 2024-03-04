package com.project.findjob.repository;

import com.project.findjob.model.ChatList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatListRepository extends JpaRepository<ChatList,Long> {
     boolean existsByFromNameAndToName(String fromName,String toName);
     void deleteByFromNameAndToName(String fromName,String toName);
     List<ChatList> findByFromName(String fromname);
     List<ChatList> findByToName(String Toname);
     ChatList findByFromNameAndToName(String fromName,String toName);

}
