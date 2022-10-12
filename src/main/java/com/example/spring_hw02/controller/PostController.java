package com.example.spring_hw02.controller;

import com.example.spring_hw02.dto.AllPostResponseDto;
import com.example.spring_hw02.dto.PostRequestDto;
import com.example.spring_hw02.dto.PostResponseDto;
import com.example.spring_hw02.service.PostService;
import com.example.spring_hw02.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    //전체조회
    @GetMapping("/post")
    public List<AllPostResponseDto> getAll() {
        return postService.getAll();
    }

    //글 작성
    @PostMapping("/post")
    public void creatPost(@RequestBody PostRequestDto postRequestDto) {
        Long currentId = SecurityUtil.getCurrentMemberId();
        postService.createPost(postRequestDto, currentId);
    }

    //글 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getOne(@PathVariable Long id) {
        return postService.getOne(id);
    }

    //글 수정
    @PutMapping("/post/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePost(id, postRequestDto);
    }

    //글 삭제
    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
