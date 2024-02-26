package com.project.findjob.dto;

import com.project.findjob.model.Personality;
import com.project.findjob.model.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private boolean applyed;
    private List<Personality> personalitys = new ArrayList<>();
    private List<Resume> resumes = new ArrayList<>();

}
