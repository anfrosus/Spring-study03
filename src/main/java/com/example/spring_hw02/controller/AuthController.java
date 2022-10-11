package com.example.spring_hw02.controller;

import com.example.spring_hw02.dto.MemberResponseDto;
import com.example.spring_hw02.dto.AuthRequestDto;
import com.example.spring_hw02.dto.jwtdto.TokenDto;
import com.example.spring_hw02.dto.jwtdto.TokenRequestDto;
import com.example.spring_hw02.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")     //아래 ResponseEntity 는 서블릿꺼
    public ResponseEntity<MemberResponseDto> signup(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.signup(authRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.login(authRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}