package com.example.spring_hw02.dto;


import com.example.spring_hw02.model.Comment;
import com.example.spring_hw02.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponseDto {

    private String title;

    private String userName;

    private String content;

    private List<CommentResponseDto> commentList;


    public PostResponseDto(Post post, List<CommentResponseDto> commentDtoList) {
        this.title = post.getTitle();
        this.userName = post.getMember().getUserName();
        this.content = post.getContent();
        this.commentList = commentDtoList;
    }
}
