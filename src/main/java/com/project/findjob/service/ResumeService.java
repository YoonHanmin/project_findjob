package com.project.findjob.service;

import com.project.findjob.model.Employment;
import com.project.findjob.model.Resume;
import com.project.findjob.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public String findMyApply(String userid){

    Resume resume = resumeRepository.findByUserid(userid);
        List<Employment> employments = resume.getEmployments();
        for ( Employment employment : employments){

        }
    return "";
    }
}
