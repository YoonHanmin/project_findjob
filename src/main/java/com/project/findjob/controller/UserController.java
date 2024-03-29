package com.project.findjob.controller;

import com.project.findjob.model.*;
import com.project.findjob.repository.*;
import com.project.findjob.service.EmploymentService;
import com.project.findjob.service.ResumeService;
import com.project.findjob.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final PersonalityRepository personalityRepository;
    private final EmployRepository employRepository;
    private final EmploymentService employmentService;
    private final StoreRepository storeRepository;
    private final ChatRepository chatRepository;
    private final ChatListRepository chatListRepository;
    // 아이디 중복확인
    @GetMapping("/regist/exists/{userid}")
    @ResponseBody
    public ResponseEntity<Boolean> checkId(@PathVariable("userid") String userid){
        boolean a = userService.checkUserid(userid);
        log.info("@# 아이디 ==>>"+a);
        return ResponseEntity.ok(userService.checkUserid(userid));
    }

//회원가입
    @PostMapping("/regist")
    public String regist(User user){
        log.info("@# User ==>"+user.getUserid());
        log.info("@# UserPW ==>"+user.getPassword());
        log.info("@# UserType ==>"+user.getType());
        if(user.getType().equals("user")){
        Role role = new Role();
        role.setId(1L); // ROLE_USER
        user.getRoles().add(role);
        } else if(user.getType().equals("owner")){
            Role role = new Role();
            role.setId(2L); // ROLE_OWNER
            user.getRoles().add(role);
        }
        userService.save(user);
    return "redirect:/login";
    }

//    프로필사진 업로드
    @PostMapping("/user/profile/upload")
    public  String upload(@RequestParam("uploadFile") MultipartFile file){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("@@# getName() ==>"+auth.getName());
        String filename = auth.getName();
        userService.uploadProfile(file,filename);

        return "redirect:/main";
    }
//    프로필 수정
    @PostMapping("/user/profile")
    @Transactional
    public String update(@RequestParam("job") List<Long>jobs,@RequestParam("personal")List<Long>persons, Resume resume,Model model){
        log.info("@#넘어온 job 값1 ==>"+jobs.get(0));

        if(resumeRepository.existsByUserid(resume.getUserid())) { // 기존에 resume이 있으면 삭제하고 새로삽입
            resumeRepository.deleteByUserid(resume.getUserid());
        }

//    넘어온 job값을 resume의 jobs에 할당
            for (Long jobid : jobs) {
                Job job = jobRepository.findById(jobid).orElse(null);
                resume.getJobs().add(job);
            }
            for (Long id : persons) {
                Personality person = personalityRepository.findById(id).orElse(null);
                resume.getPersonalitys().add(person);
            }



            log.info("@# update_resume의 jobs ==>" + resume.getJobs());
            Optional<User> user = userRepository.findByUserid(resume.getUserid());
            User updateuser = user.get();
            log.info("#@ 유저 이름 ==>"+resume.getGender());
            log.info("#@ 유저 이름 ==>"+resume.getPhone());
            resume.setUsername(updateuser.getUname());
            resume.setLoc(updateuser.getArea2());
            resume.setProfile_img(updateuser.getProfileurl());
            updateuser.setEnabled(true);
            userRepository.save(updateuser);

        resumeRepository.save(resume);
        return "redirect:/main";
    }
//    내 프로필 보기
    @GetMapping("/user/profile/{userid}")
    public String myProfile(@PathVariable("userid") String id, Model model){
        Resume resume = resumeRepository.findByUserid(id);
        log.info("@# personalitys 1번 네임==>"+resume.getPersonalitys().get(0).getName());
        model.addAttribute("resume",resume);
        return "/user/profile";
    }

    @GetMapping("/main/{profileurl}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable("profileurl") String profileurl){
        log.info("@# 프로필 이미지 =>"+profileurl);
//			업로드 파일 경로 + 이름
        File file = new File("D:\\dev\\ProjectUpload\\profile\\"+profileurl);
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
    @GetMapping("/user/findjob")
    public String find(Model model,
                       @PageableDefault(size = 8) Pageable pageable,
                       @RequestParam(required = false,name = "area1") String area1,
                       @RequestParam(required = false,name = "area2") String area2,
                       @RequestParam(required = false,name = "job") Long job,
                       @RequestParam(required = false,name = "time") String time){


        Page<Employment> list = null;
        log.info("area1 ==>"+area1);
        log.info("area2 ==>"+area2);
        log.info("job ==>"+job);
        log.info("time ==>"+time);
        if(area1 == null && area2 == null && time == null && job == null){
            list = employRepository.findAll(pageable);
            int startPage = 1; // 1페이지부터 시작
            int endPage = list.getTotalPages();
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("list", list);
        }else {

            list = employRepository.findByArea1ContainingAndArea2ContainingAndTimeContaining(area1, area2, time, pageable);
            int startPage = 1; // 1페이지부터 시작
            int endPage = list.getTotalPages(); //Page객체의 getTotalPages메소드를 통해 총 페이지수 구할수있음
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("list", list);
            log.info("@# ajax성공!! ==> " + list.getTotalElements());
        }
//        }else {
//            list = employRepository.findByArea1ContainingAndArea2ContainingAndJobAndTimeContaining(area1, area2, job, time, pageable);
//            log.info("파라미터값 ==>" + job);
//            log.info("파라미터값 ==>" + time);
//            int startPage = 1; // 1페이지부터 시작
//            int endPage = list.getTotalPages(); //Page객체의 getTotalPages메소드를 통해 총 페이지수 구할수있음
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//            model.addAttribute("list", list);
//            log.info("@# ajax성공!! ==> " + list.getTotalElements());
//
//        }

        return "/user/findjob";
    }

//    알바 지원 메소드
        @PostMapping("/user/apply")
    public String apply(@RequestParam("resume")String resume_id,@RequestParam("employ")Long employ_id){
           Resume resume =resumeRepository.findByUserid(resume_id);
           Optional<Employment> employmentOptional = employRepository.findById(employ_id);
           Employment employ = employmentOptional.get();
           employ.getResumes().add(resume);
            resume.getEmployments().add(employ);
            resumeRepository.save(resume);
            employRepository.save(employ);
            log.info("@# resumeid ==>"+resume_id);
            log.info("@# employ id ==>"+employ_id);

            log.info("지원완료!!");
            return "redirect:/main";
        }

        @GetMapping("/user/myApply")
    public String myApply(Model model,Authentication auth){
        String userid = auth.getName();
            Resume resume = resumeRepository.findByUserid(userid);
            model.addAttribute("resume",resume);
        return "user/myApply";
        }
    @GetMapping("/finduser")
    public String finduser(Model model,Authentication authentication){
        String ownerId = authentication.getName();
        model.addAttribute("ownerId",ownerId);
       Employment employment = employRepository.findByOwnerid(ownerId);
        Long employId = employment.getId();
        model.addAttribute("employId",employId);
        log.info(ownerId);
        return "/owner/finduser";
    }

    @GetMapping("/finduser/loc")
    public @ResponseBody List<Resume> finduserLoc(Model model,@RequestParam("ownerId") String ownerId){
        Store store = storeRepository.findByUserid(ownerId);
        String loc = store.getArea2();
        List<Resume> resumes = resumeService.findByLoc(loc);

        return resumes;
    }
    @GetMapping("/finduser/per")
    public @ResponseBody List<Resume> finduserPer(Model model,@RequestParam("ownerId") String ownerId){
       Employment employ = employRepository.findByOwnerid(ownerId);
        List<Resume> resumes = resumeService.findPersonality(employ);

        return resumes;
    }


    @GetMapping("/message")
    public String message(Model model,Authentication auth){
        String userid = auth.getName();
        Resume resume = resumeRepository.findByUserid(userid);
        model.addAttribute("resume",resume);
        return "message";
    }

    @GetMapping("user/messageList")
    public String msgList(Model model,Authentication auth){
        User user = (User) auth.getPrincipal();
        String uname = user.getUname();
//        채팅방 불러오기
        List<ChatList> chatList = chatListRepository.findByToName(uname);
        for(ChatList chatlist : chatList){
            Chat chat = chatRepository.findTopByChatListIdOrderByIdDesc(chatlist.getId());
            chatlist.setLastchat(chat.getData());
            chatlist.setTime(chat.getTime());
            chatListRepository.save(chatlist);
        }
        model.addAttribute("chatList",chatList);
        return "user/messageList";
    }
}
