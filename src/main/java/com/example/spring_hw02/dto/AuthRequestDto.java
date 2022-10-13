package com.example.spring_hw02.dto;

import com.example.spring_hw02.model.Member;
import com.example.spring_hw02.model.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AuthRequestDto {

//    @NotBlank(message = "아이디를 입력하세요우")
//    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디 형식을 확인하세요")
    private String userName;
//    @NotBlank(message = "비밀번호를 확인하세요우")
//    @Pattern(regexp = "^[a-z0-9]{4,32}$", message = "비밀번호 형식을 확인하세요")
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

