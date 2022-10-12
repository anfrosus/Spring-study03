package com.example.spring_hw02.service;

import com.example.spring_hw02.dto.MemberResponseDto;
import com.example.spring_hw02.dto.AuthRequestDto;
import com.example.spring_hw02.dto.jwtdto.TokenDto;
import com.example.spring_hw02.dto.jwtdto.TokenRequestDto;
import com.example.spring_hw02.jwt.TokenProvider;
import com.example.spring_hw02.model.Member;
import com.example.spring_hw02.model.RefreshToken;
import com.example.spring_hw02.repository.MemberRepository;
import com.example.spring_hw02.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입 로직
    @Transactional
    public MemberResponseDto signup(AuthRequestDto authRequestDto) {
        Pattern patternUserName = Pattern.compile("^[a-zA-Z0-9]{4,12}$");
        Matcher matcherUserName = patternUserName.matcher(authRequestDto.getUserName());
        Pattern patternPassword = Pattern.compile("^[a-z0-9]{4,32}$");
        Matcher matcherPassword = patternPassword.matcher(authRequestDto.getPassword());

        if (!matcherUserName.find() || !matcherPassword.find()){
            throw new IllegalArgumentException("아이디와 비밀번호 형식을 확인하세요");
        }
        if (memberRepository.existsByUserName(authRequestDto.getUserName())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        if (!authRequestDto.getPassword().equals(authRequestDto.getDoubleCheck())){
            throw new RuntimeException("비밀번호를 확인해 주세요");
        }
        Member member = authRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    //로그인 서비스
    @Transactional
    public TokenDto login(AuthRequestDto authRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성 (Dto안에 Id,pw 를 담은 인증 객체 생성 함수확인)
        // 그러면 아래에 이 객체를 담아 security에게 넘김
        UsernamePasswordAuthenticationToken authenticationToken = authRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        //    security 인증완료된 객체느낌
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 위의 인증 정보(객체) 안에 Id, Pw 들어가 있겠지
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }


    //재발급
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
