package com.project.findjob.controller;

import com.project.findjob.model.Employment;
import com.project.findjob.model.Personality;
import com.project.findjob.model.Store;
import com.project.findjob.model.User;
import com.project.findjob.repository.EmployRepository;
import com.project.findjob.repository.PersonalityRepository;
import com.project.findjob.repository.StoreRepository;
import com.project.findjob.repository.UserRepository;
import com.project.findjob.service.EmploymentService;
import com.project.findjob.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OwnerController {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final EmployRepository employRepository;
    private final PersonalityRepository personalityRepository;
    private final EmploymentService employmentService;
    private final StoreService storeService;
//    내 가게 보기
    @GetMapping("/owner/store/{userid}")
    public String store(@PathVariable("userid")String userid, Model model){
        Store store = storeRepository.findByUserid(userid);
        model.addAttribute("store",store);

        return "owner/store";
    }

//    내 가게 등록
    @PostMapping("/owner/store")
    public String addStore(@RequestParam("storeimg") MultipartFile[] files, Store store){
        log.info("클라이언트에서 보낸 아이디"+store.getUserid());
        log.info("클라이언트에서 보낸 가게정보"+store);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userId = user.getUserid();

//        가게사진 등록
        String directory = "D:\\dev\\ProjectUpload\\store";
        List<String> uploadedFiles = new ArrayList<>();
        int count = 1;
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileName = userId + count + originalFileName.substring(originalFileName.lastIndexOf("."));

            File dest = new File(directory+ "/" + fileName);

            try {
                file.transferTo(dest);
                uploadedFiles.add(fileName);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
                // 파일 업로드 실패 시 예외 처리
            }
        }
//        사진파일 저장
        if(uploadedFiles.size()==3) {
                store.setStoreimg1(uploadedFiles.get(0));
                store.setStoreimg2(uploadedFiles.get(1));
                store.setStoreimg3(uploadedFiles.get(2));
        } else if (uploadedFiles.size()==2) {
            store.setStoreimg1(uploadedFiles.get(0));
            store.setStoreimg2(uploadedFiles.get(1));
        }else {
            store.setStoreimg1(uploadedFiles.get(0));
        }

        if(storeRepository.existsByUserid(userId)) { // 기존에 store이 있으면 수정
            Store updatestore = storeRepository.findByUserid(userId);
            updatestore.setStorename(store.getStorename());
            updatestore.setJob(store.getJob());
            updatestore.setPhone(store.getPhone());
            updatestore.setAddress(store.getAddress());
            updatestore.setArea1(store.getArea1());
            updatestore.setArea2(store.getArea2());
            updatestore.setStoreimg1(store.getStoreimg1());
            updatestore.setStoreimg2(store.getStoreimg2());
            updatestore.setStoreimg3(store.getStoreimg3());
            storeRepository.save(updatestore);
        }else {
            storeRepository.save(store);
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "/main";
    }
// 가게 이미지 출력
    @GetMapping("store/img/{userid}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable("userid") String profileurl){
        log.info("@# 프로필 이미지 =>"+profileurl);
//			업로드 파일 경로 + 이름
        File file = new File("D:\\dev\\ProjectUpload\\store\\"+profileurl);
        ResponseEntity<byte[]> result = null;
        HttpHeaders header = new HttpHeaders();
        try {
//			HttpHeaders 객체 생성후 add(컨텐츠타입,경로)메소드로 파일타입을 HTTP 헤더에 추가
            header.add("Content-Type", Files.probeContentType(file.toPath()));
//				파일 정보를 byte 배열로 복사+헤더정보+http상태 정상을 결과에 저장
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }
    @GetMapping("owner/store")
    public String mystore(){
        return "owner/storeChange";
    }

    @GetMapping("owner/myEmploy")
    public String myemploy(Model model,Authentication auth){
        List<Employment> employ = employRepository.findByOwnerid(auth.getName());
        model.addAttribute("employ",employ);

        return "owner/myEmploy";
    }
    @GetMapping("owner/showEmploy/{id}")
    public String showEmploy(@PathVariable("id")Long id, Model model,Authentication auth){
        Optional<Employment> employmentOptional = employRepository.findById(id);
        Employment employ = employmentOptional.get();

        model.addAttribute("employ",employ);

        return "owner/viewEmploy";
    }
    @GetMapping("/showEmploy/{id}")
    public String showEmploy2(@PathVariable("id")Long id, Model model,Authentication auth){
        Optional<Employment> employmentOptional = employRepository.findById(id);
        Employment employ = employmentOptional.get();
        String userid = auth.getName();
        model.addAttribute("employ",employ);
        boolean applyed = employmentService.existById(id,userid);
        model.addAttribute("applyed",applyed);
        return "/showEmploy";
    }

    @GetMapping("/owner/addEmployment")
    public String addEmployment(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userId = user.getUserid();
        Store store = storeRepository.findByUserid(userId);
        model.addAttribute("store",store);
        return "owner/addEmployment";
    }

    @PostMapping("/owner/addEmployment")
    public String addEmploy(@RequestParam("personality") List<Long> personalitys,
                            Employment employment){
        //    넘어온 personality값을 resume의 jobs에 할당
//        Time timeValue = Time.valueOf(selectedTime);
        log.info("@# 넘어온 employment 타입확인 ==>"+employment);
        for (Long id : personalitys) {
          Optional<Personality> personalityOptional = personalityRepository.findById(id);
            Personality personality = personalityOptional.get();
            employment.getPersonalitys().add(personality);
        }
        employRepository.save(employment);
        return "main";
    }
// 가게이미지 보기
    @GetMapping("/findjob/img/{storeimg}")
    @ResponseBody
    public ResponseEntity<byte[]> getstoreimg(@PathVariable("storeimg") String storeimg){

//			업로드 파일 경로 + 이름
        File file = new File("D:\\dev\\ProjectUpload\\store\\"+storeimg);
        ResponseEntity<byte[]> result = null;
        HttpHeaders header = new HttpHeaders();
        try {
//			HttpHeaders 객체 생성후 add(컨텐츠타입,경로)메소드로 파일타입을 HTTP 헤더에 추가
            header.add("Content-Type", Files.probeContentType(file.toPath()));
//				파일 정보를 byte 배열로 복사+헤더정보+http상태 정상을 결과에 저장
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/owner/chat")
    public String chat(){
        return "owner/chat";
    }
}
