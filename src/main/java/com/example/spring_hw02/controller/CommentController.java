package com.example.spring_hw02.controller;

import com.example.spring_hw02.dto.CommentRequestDto;
import com.example.spring_hw02.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    //댓글 조회
    //이거는 글 조회할때 같이 뜨게끔 하자.
    //댓글 작성
    @PostMapping("/comment/{postId}")
    //글의 Id 가 넘어온다고 가정
    public void createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(postId, commentRequestDto);
    }
    //댓글 수정
    @PutMapping("/comment/{commentId}")
    //수정할 댓글의 id가 넘어온다고 가정
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId, commentRequestDto);
    }
    //댓글 삭제
    @DeleteMapping
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
