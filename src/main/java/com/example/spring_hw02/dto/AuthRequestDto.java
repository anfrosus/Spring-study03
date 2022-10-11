package com.example.spring_hw02.dto;

import com.example.spring_hw02.model.Member;
import com.example.spring_hw02.model.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class AuthRequestDto {

    private String userName;
    private String password;

    private String doubleCheck;


    //회원 가입 시 SignupRequestDto 값을 Member 에 옮겨 생성할 때 사용
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .authority(MemberRoleEnum.ROLE_USER)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userName, password);
    }
}

