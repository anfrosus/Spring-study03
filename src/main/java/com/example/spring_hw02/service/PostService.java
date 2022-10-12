package com.example.spring_hw02.service;

import com.example.spring_hw02.dto.AllPostResponseDto;
import com.example.spring_hw02.dto.CommentResponseDto;
import com.example.spring_hw02.dto.PostResponseDto;
import com.example.spring_hw02.dto.PostRequestDto;
import com.example.spring_hw02.model.Comment;
import com.example.spring_hw02.model.Member;
import com.example.spring_hw02.model.Post;
import com.example.spring_hw02.repository.CommentRepository;
import com.example.spring_hw02.repository.MemberRepository;
import com.example.spring_hw02.repository.PostRepsitory;
import com.example.spring_hw02.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepsitory postRepsitory;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

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
    public void createPost(PostRequestDto postRequestDto, Long currentMemberId) {

        Member member = memberRepository.findById(currentMemberId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원 정보가 없습니다.")
        );
        Post post1 = new Post(postRequestDto, member);

        postRepsitory.save(post1);
    }

    //하나 조회하기
    @Transactional(readOnly = true)
    public PostResponseDto getOne(Long id) {
        Post post1 = postRepsitory.findById(id).orElseThrow(
                () -> new IllegalArgumentException("못찾았으요")
        );
        //댓글 넣어주기
        List<Comment> commentList = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> dtoList= new ArrayList<>();
        for(Comment a : commentList){
            dtoList.add(new CommentResponseDto(a));
        }
        return new PostResponseDto(post1, dtoList);
    }

    //수정하기
    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto) {
        //위 id는 해당 글의 id가 들어온다고 가정
        //글 아이디로 db찔러서 user아이디를 가져오고 현재로그인된 id를 대조하기
        Post post1 = postRepsitory.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다."));
        Long authorId = post1.getMember().getId();
        Long currentId = SecurityUtil.getCurrentMemberId();
        if (authorId == currentId) {
            post1.update(postRequestDto);
            //트랜잭셔널 붙어있어서 업데이트하면 자동으로 세이부!
        } else {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
    }

    //삭제하기
    @Transactional
    public void deletePost(Long id) {
        //넘어온 id는 해당 글의 id가 들어온다고 가정
        //위와 마찬가지로 현재로그인 id를 글의 저장되어있는 MEMBER_ID와 대조하기
        Post post1 = postRepsitory.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다."));
        Long authorId = post1.getMember().getId();
        Long currentId = SecurityUtil.getCurrentMemberId();
        if (authorId == currentId) {
            postRepsitory.deleteById(id);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
    }
}
