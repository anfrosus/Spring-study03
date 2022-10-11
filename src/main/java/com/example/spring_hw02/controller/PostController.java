package com.example.spring_hw02.controller;

import com.example.spring_hw02.config.MemberDetailsImpl;
import com.example.spring_hw02.dto.AllPostResponseDto;
import com.example.spring_hw02.dto.PostResponseDto;
import com.example.spring_hw02.dto.PostRequestDto;
import com.example.spring_hw02.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //전체조회
    @GetMapping("/api/post")
    public List<AllPostResponseDto> getAll() {
        return postService.getAll();
    }

    //글 작성
    @PostMapping("/api/post")
    public void creatPost(@RequestBody PostRequestDto postRequestDto,
                          @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        postService.createPost(postRequestDto, memberDetails.getMember());
    }

    //글 조회
    @GetMapping("/api/post/{id}")
    public PostResponseDto getOne(@PathVariable Long id) {
        return postService.getOne(id);
    }

    //글 수정
    @PutMapping("/api/post/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                           @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        postService.updatePost(id, postRequestDto);
    }

    //글 삭제
    @DeleteMapping("api/post/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
