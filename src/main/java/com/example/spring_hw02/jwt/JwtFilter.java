package com.example.spring_hw02.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";

    public static final String REFRESH_HEADER = "Refresh";

    public static final String REFRESH_PREFIX = "Refresh";

    private final TokenProvider tokenProvider;


    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {


        // 1. Request Header 에서 토큰을 꺼냄
        // (아래 구현해놓은 헤더에서 토큰 꺼내오는 메소드)
        String jwt = resolveToken(request);
//        String jwtR = resolveRefreshToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        // 아래 프로바이더에 있는 메소드 검증로직 있더라
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //로그인, 토큰 발급 후 추가적인 요청을 보낼 때 토큰 검증과 함께 시큐리티컨텍스트에 저장.
        }
            //어디로 넘어가나요 ㅠㅠ?
            //else여야 하는거 아닌가? 실패시 필터체인으로 가는거아니여?
            //else하니까 글작성이 안되네
        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 영광의 상처..
    private String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_HEADER);
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(REFRESH_PREFIX)) {
            return refreshToken.substring(7);

        }
        return null;
    }
}