package com.example.spring_hw02.dto;

import com.example.spring_hw02.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String userName;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUserName());
    }
}