package com.project.findjob.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Entity
@Data
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeid;
    private String ownerid;
    private String title;
    private String content;
    private Date uploaddate;
    private int pay;
    private String pay_type;
    private String personality;
    private Time start_time;
    private Time end_time;
    private String time;

}
