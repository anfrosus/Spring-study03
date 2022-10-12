package com.example.spring_hw02.controller;

import com.example.spring_hw02.dto.CommentRequestDto;
import com.example.spring_hw02.exception.NoAuthorityException;
import com.example.spring_hw02.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            return new ResponseEntity(commentService.createComment(postId, commentRequestDto), HttpStatus.valueOf(200));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(404));
        } catch (NoAuthorityException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    //댓글 수정
    @PutMapping("/comment/{commentId}")
    //수정할 댓글의 id가 넘어온다고 가정
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            return new ResponseEntity(commentService.updateComment(commentId, commentRequestDto), HttpStatus.valueOf(200));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(404));
        } catch (NoAuthorityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    //댓글 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.valueOf(200));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        } catch (NoAuthorityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }
}
