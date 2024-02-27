package com.project.findjob.service;

import com.project.findjob.model.Resume;
import com.project.findjob.repository.ResumeRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;



    public List<Resume> findByLoc(String area2){
       List<Resume> resumes = resumeRepository.findByLoc(area2);

       return resumes;
    }
}
