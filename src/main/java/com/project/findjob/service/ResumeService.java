package com.project.findjob.service;

import com.project.findjob.dto.ResumeSearchDto;
import com.project.findjob.model.Employment;
import com.project.findjob.model.Personality;
import com.project.findjob.model.Resume;
import com.project.findjob.repository.PersonalityRepository;
import com.project.findjob.repository.ResumeRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final PersonalityRepository personalityRepository;


    public List<Resume> findByLoc(String area2) {
        List<Resume> resumes = resumeRepository.findByLoc(area2);

        return resumes;
    }

    //    특정 employment의 특성과 일치하는 갯수가 가장 많은 순으로 Resume를 List에 담아서 반환
    public List<Resume> findPersonality(Employment employment) {

//        1) 탐색을 위한 사전작업
//        모든 Resume List 얻는다.
        List<Resume> resumes = resumeRepository.findAll();
//        해당 ResumeList를 DTO List로 변환
            List<ResumeSearchDto> dtoList =copyDtoList(resumes);
//        찾을 특성을 객체에서 id값만 담은 List로 변환
        List<Long> searchList = new ArrayList<>();
        List<Long> resumeList = new ArrayList<>();

        List<Personality> employ_Personality = employment.getPersonalitys();
        for (Personality personality : employ_Personality) {
            searchList.add(personality.getId());
        }
        log.info("@# searchList ==>"+searchList);


//        전체 Resume을 대상으로 탐색시작
        for (ResumeSearchDto dto : dtoList) {
            int count = 0;
            log.info("@# resume id ==>"+dto.getId());
//           1. 첫번째 resume의 특성값을 객체 -> 리스트로 변환
            for (Personality personality : dto.getPersonalitys()){
               resumeList.add(personality.getId());
            }
//            2. 찾을 특성값 순서대로 첫번째 resume의 특성값과 일치하면 카운트
            for (Long id : searchList){
                if(resumeList.contains(id)){
//                    log.info("@# 찾은 id값 ==>"+id);
                    count ++;
                }
            }
//            3. 카운트수를 dto에 담는다.
            dto.setCount(count);
//            4. 하나의 resume 탐색후 초기화
            resumeList.clear();
        }

        for(ResumeSearchDto dto : dtoList){
            log.info("@# 검색결과 count ===>"+dto.getCount());
        }

//      dto의 일치하는 갯수 count를 기준으로 정렬
        Collections.sort(dtoList, Comparator.comparingInt(ResumeSearchDto::getCount).reversed());
        resumes =convertDto(dtoList,resumeRepository);
        for (Resume resume : resumes) {
            log.info("@# 정렬후 ==>" + resume.getId());
        }


        return resumes;
    }

//    Resume Entity -> Resume Dto 변환
    public static ResumeSearchDto copyDto(Resume resume){
        ResumeSearchDto dto = new ResumeSearchDto();
        dto.setPersonalitys(resume.getPersonalitys());
        dto.setId(resume.getId());
        return dto;
    }
    //    Resume Dto -> Resume Entity 변환
    public static List<Resume> convertDto(List<ResumeSearchDto> dtoList,ResumeRepository resumeRepository){
        List<Resume> resultList = new ArrayList<>();
        for (ResumeSearchDto dto : dtoList){
            Optional<Resume> resumeOptional = resumeRepository.findById(dto.getId());
            resumeOptional.ifPresent(resultList::add);
        }
        return resultList;
    }

    public static List<ResumeSearchDto> copyDtoList(List<Resume> resumes){
        List<ResumeSearchDto> list = new ArrayList<>();
        for (Resume resume : resumes){
            list.add(copyDto(resume));
        }
        return list;
    }


}