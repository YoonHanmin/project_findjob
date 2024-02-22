package com.project.findjob.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "personalitys")

//    role테이블에서 user정보 조회
    private List<Resume> resumes;
}
