package com.example.spring_hw02.controller;

import com.example.spring_hw02.dto.AllPostResponseDto;
import com.example.spring_hw02.dto.PostRequestDto;
import com.example.spring_hw02.exception.NoAuthorityException;
import com.example.spring_hw02.service.PostService;
import com.example.spring_hw02.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> creatPost(@RequestBody PostRequestDto postRequestDto) {
        try {Long currentId = SecurityUtil.getCurrentMemberId();
            return new ResponseEntity(postService.createPost(postRequestDto, currentId), HttpStatus.valueOf(200));
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    //글 조회
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {return new ResponseEntity(postService.getOne(id), HttpStatus.valueOf(200));}
        catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(404));
        }
    }

    //글 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        try {return new ResponseEntity(postService.updatePost(id, postRequestDto), HttpStatus.valueOf(200));}
        catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(404));
        }catch (NoAuthorityException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    //글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {return new ResponseEntity(postService.deletePost(id), HttpStatus.valueOf(200));}
        catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(404));
            //e.getMessage()가 바디부분, (아무거나 올 수 있도록 <?>)
            //ResponseEntity<?>
        }catch (NoAuthorityException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

}
