package com.example.spring_hw02.dto;

import com.example.spring_hw02.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private String userName;

    private String comment;

    public CommentResponseDto(Comment comment) {
        this.userName = comment.getMember().getUserName();
        this.comment = comment.getComment();
    }
}
