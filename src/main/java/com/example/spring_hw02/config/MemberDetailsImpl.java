//package com.example.spring_hw02.config;
//
//import com.example.spring_hw02.model.Member;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
////컨트롤러에 Member 를 통째로 넘겨주려고 만들었는데 다른 방법이 있을까?
//public class MemberDetailsImpl implements UserDetails {
//
//    private final Member member;
//
//    public MemberDetailsImpl(Member member) {
//        this.member = member;
//    }
//
//    public Member getMember() {
//        return member;
//    }
//
//    //권한 부여
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
