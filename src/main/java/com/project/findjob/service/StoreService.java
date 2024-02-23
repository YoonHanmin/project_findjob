package com.project.findjob.service;

import com.project.findjob.repository.StoreRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


}
