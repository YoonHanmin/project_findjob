package com.project.findjob.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileImg;
    private String education;
    private String degree;
    private Double score;
    private String personality;
    private String liketime;


    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키 이름 지정
    private User user;

    @ManyToMany
    @JoinTable(
            name = "resume_job",
            joinColumns = @JoinColumn(name="resume_id"),
            inverseJoinColumns = @JoinColumn(name="job_id")
    )
    //    job테이블에서 job 조회
    private List<Job> jobs = new ArrayList<>();
}
