package com.project.findjob.service;

import com.project.findjob.dto.EmploymentDto;
import com.project.findjob.model.Employment;
import com.project.findjob.model.Resume;
import com.project.findjob.repository.EmployRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmploymentService {
    private final EmployRepository employRepository;

    public boolean existById(Long employ_id,String userid){
        Optional<Employment> employmentOptional = employRepository.findById(employ_id);
        Employment employ = employmentOptional.get();
        List<Resume> resumes= employ.getResumes();
        boolean result = false;
        for(Resume resume : resumes){
            if(resume.getUserid().equals(userid)){
                result = true;
            }
        }
        return result;
    }

    public EmploymentDto copyDto(Long id,String userid){
        Optional<Employment> employmentOptional = employRepository.findById(id);
        Employment employment = employmentOptional.get();

//        빌더패턴으로 객체 복사
        EmploymentDto employmentDto = EmploymentDto.builder()
                .id(employment.getId())
                .storeid(employment.getStoreid())
                .ownerid(employment.getOwnerid())
                .title(employment.getTitle())
                .job(employment.getJob())
                .content(employment.getContent())
                .uploaddate(employment.getUploaddate())
                .pay(employment.getPay())
                .pay_type(employment.getPay_type())
                .start_time(employment.getStart_time())
                .end_time(employment.getEnd_time())
                .time(employment.getTime())
                .location(employment.getLocation())
                .loc1(employment.getArea1())
                .loc2(employment.getArea2())
                .personalitys(employment.getPersonalitys())
                .resumes(employment.getResumes())
                .applyed(false)
                .build();

        if(existById(id, userid)){
                employmentDto.setApplyed(true);
        }
        else{  employmentDto.setApplyed(false);}

        return employmentDto;
    }
}
