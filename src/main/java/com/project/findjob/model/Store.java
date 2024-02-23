package com.project.findjob.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String storename;
    private Long job;
    private String address;
    private String area1;
    private String area2;
    private String phone;
    private String storeimg1;
    private String storeimg2;
    private String storeimg3;

}
