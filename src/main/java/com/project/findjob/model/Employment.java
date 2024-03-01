package com.project.findjob.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    private String storename;
    private String ownerid;
    private String storeimg;
    private String ownername;
    private String title;
    private String phone;
    private Long job;
    private String content;
    @Temporal(TemporalType.DATE) // 해당 컬럼이 DATE타입임을 알려줌
    private Date uploaddate;
    private int pay;
    private String location;
    private String area1;
    private String area2;
    private String pay_type;
    private String start_time;
    private String end_time;
    private String time;
    private LocalDate deadline;
    private String status;

// Date타입 삽입전에 EntityManager가 해당 메소드 수행
    @PrePersist
    public void prePersist() {
        this.uploaddate = new Date(); // 현재 날짜 설정
    }

    @ManyToMany
    @JoinTable(
            name = "employment_personality",
            joinColumns = @JoinColumn(name="employ_id"),
            inverseJoinColumns = @JoinColumn(name="personality_id")
    )
    //    personality테이블에서  personality 조회
    private List<Personality> personalitys = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employment_resume",
            joinColumns = @JoinColumn(name="employ_id"),
            inverseJoinColumns = @JoinColumn(name="resume_id")
    )
    private List<Resume> resumes = new ArrayList<>();
}
