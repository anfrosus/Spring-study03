package com.example.spring_hw02.config;

import com.example.spring_hw02.jwt.JwtAccessDeniedHandler;
import com.example.spring_hw02.jwt.JwtAuthenticationEntryPoint;
import com.example.spring_hw02.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  //웹시큐리티 사용선언
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //우리는 토큰 방식을 사용하기에 disable
                .csrf().disable()

                //시큐리티 세션 끄기
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //exceptionHandling 할 때 우리가 만든 클래스 추가
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                //HttpServletRequest 를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .authorizeRequests()
                //토큰이 없는 상태에서 들어오는 요청
                //아래 api 에는 인증 없이 접근을 허용하겠다
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/post").permitAll()
                .antMatchers(HttpMethod.GET,"/api/post/{id}").permitAll()

                //그 외에는 모두 인증을 받아야 한다.
                .anyRequest().authenticated()

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

    }

    @Override
    public void configure(WebSecurity web){
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
}
