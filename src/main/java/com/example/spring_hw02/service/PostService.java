package com.example.spring_hw02.service;

import com.example.spring_hw02.dto.AllPostResponseDto;
import com.example.spring_hw02.dto.PostResponseDto;
import com.example.spring_hw02.dto.PostRequestDto;
import com.example.spring_hw02.model.Member;
import com.example.spring_hw02.model.Post;
import com.example.spring_hw02.repository.PostRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepsitory postRepsitory;

    //모두 조회하기
    @Transactional(readOnly = true)
    public List<AllPostResponseDto> getAll() {
        List<Post> listP = postRepsitory.findByOrderByCreateAtDesc();
        List<AllPostResponseDto> listR = new ArrayList<>();
        for (Post post1 : listP){
            listR.add(new AllPostResponseDto(post1));
        }
        return listR;
    }

    //게시글 작성하기
    @Transactional
    public void createPost(PostRequestDto postRequestDto, Member member) {

        Post post1 = new Post(postRequestDto, member);

        postRepsitory.save(post1);
    }

    //하나 조회하기
    @Transactional(readOnly = true)
    public PostResponseDto getOne(Long id) {
        Post post1 = postRepsitory.findById(id).orElseThrow(
                () -> new IllegalArgumentException("못찾았으요")
        );
        return new PostResponseDto(post1);
    }

    //수정하기
    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto) {
        Post post1 = postRepsitory.findById(id).orElseThrow(() -> new IllegalArgumentException("못바꿨으요"));
        post1.update(postRequestDto);
    }

    //삭제하기
    @Transactional
    public void deletePost(Long id) {
        postRepsitory.deleteById(id);
    }
}
