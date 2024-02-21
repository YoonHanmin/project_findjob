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
    @Column(name="profileImg")
    private String profile_img;
    @Column(name = "userid")
    private String userid;
    private String education;
    private String degree;
    private Double score;
    private String personality;
    private String liketime;

    @ManyToMany
    @JoinTable(
            name = "resume_job",
            joinColumns = @JoinColumn(name="resume_id"),
            inverseJoinColumns = @JoinColumn(name="job_id")
    )
    //    job테이블에서 job 조회
    private List<Job> jobs = new ArrayList<>();
}
