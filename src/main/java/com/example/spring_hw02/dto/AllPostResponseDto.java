package com.example.spring_hw02.dto;

import com.example.spring_hw02.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AllPostResponseDto {

    private String title;

    private String userName;

    private LocalDateTime createAt;

    public AllPostResponseDto(Post post) {
        this.title = post.getTitle();
        this.userName = post.getMember().getUserName();
        this.createAt = post.getCreateAt();
    }
}
