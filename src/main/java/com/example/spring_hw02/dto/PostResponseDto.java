package com.example.spring_hw02.dto;


import com.example.spring_hw02.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private String title;

    private String userName;

    private String content;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.userName = post.getMember().getUserName();
        this.content = post.getContent();
    }
}
