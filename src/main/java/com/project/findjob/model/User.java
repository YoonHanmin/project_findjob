package com.project.findjob.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min=4,max=20, message = "ID는 4~20자 입니다.")
    private String userid;
    private String useremail;
    private String uname;
    private String address;
    private String password;
    private String phone;
    private String gender;
    private String type;
    private LocalDate birthday;
    private String profileurl;
    private Boolean enabled;

    // Resume 엔티티와의 One-to-Many 관계 매핑
    @OneToMany(mappedBy = "user")
    private List<Resume> resumes;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    //    user테이블에서 role 조회
    private List<Role> roles = new ArrayList<>();

    // 사용자가 가진 권한정보는 loadUserBy~시점에 이미 DB에서 가져와 세팅되어있으므로 roles를 스트림 형태로 꺼내와 사용자의 권한을 리턴하는 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userid;
    }



}

