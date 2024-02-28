package com.project.findjob.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String profile_img;
    @Column(name = "userid")
    private String userid;
    private String education;
    private String degree;
    private Double score;
    private String liketime;
    private String description;
    private String username;
    private String loc;
    private String birthday;
    private String gender;
    private String phone;


    @ManyToMany
    @JoinTable(
            name = "resume_personality",
            joinColumns = @JoinColumn(name="resume_id"),
            inverseJoinColumns = @JoinColumn(name="personality_id")
    )
    //    user테이블에서 role 조회
    private List<Personality> personalitys = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "resume_job",
            joinColumns = @JoinColumn(name="resume_id"),
            inverseJoinColumns = @JoinColumn(name="job_id")
    )
    //    job테이블에서 job 조회
    private List<Job> jobs = new ArrayList<>();

    @ManyToMany(mappedBy = "resumes")
//    role테이블에서 user정보 조회
    @JsonIgnore
    private List<Employment> employments;
}
