package com.project.findjob.dto;

import com.project.findjob.model.Personality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSearchDto {
    private Long id;
    private List<Personality> personalitys = new ArrayList<>();
    private int count; // 일치하는 특성 갯수를 카운트하는 컬럼
    public ResumeSearchDto(List<Personality> personalitys){
        this.personalitys = personalitys;
    }
}

