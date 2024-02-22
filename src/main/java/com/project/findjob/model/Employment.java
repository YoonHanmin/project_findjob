package com.project.findjob.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Time start_time;
    private Time end_time;
    private String time;

    @ManyToMany
    @JoinTable(
            name = "employment_personality",
            joinColumns = @JoinColumn(name="employ_id"),
            inverseJoinColumns = @JoinColumn(name="personality_id")
    )
    //    personality테이블에서  personality 조회
    private List<Personality> personalitys = new ArrayList<>();
}
