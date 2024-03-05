package com.project.findjob.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Entity
@Slf4j
@Data
@NoArgsConstructor
public class Chat {
    public Chat(String toName,String fromName,String data,Timestamp time,Long chatListId,boolean readchat){
        this.toName = toName;
        this.fromName = fromName;
        this.data = data;
        this.time = time;
        this.chatListId = chatListId;
        this.readchat = readchat;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toName;
    private String fromName;
    private String data;
    private Timestamp time;
    private Long chatListId;
    private boolean readchat;
}
