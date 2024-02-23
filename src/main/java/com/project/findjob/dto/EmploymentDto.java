package com.project.findjob.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmploymentDto {
    private Long id;
    private Long storeid;
    private String ownerid;
    private String title;
    private Long job;
    private String content;
    private Date uploaddate;
    private int pay;
    private String pay_type;
    private String start_time;
    private String end_time;
    private String time;
    private String location;
    private String loc1;
    private String loc2;

}
