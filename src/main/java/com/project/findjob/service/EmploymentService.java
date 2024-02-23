package com.project.findjob.service;

import com.project.findjob.repository.EmployRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmploymentService {
    private final EmployRepository employRepository;


}
