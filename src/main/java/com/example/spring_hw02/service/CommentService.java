package com.example.spring_hw02.service;

import com.example.spring_hw02.dto.CommentRequestDto;
import com.example.spring_hw02.exception.NoAuthorityException;
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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepsitory postRepsitory;

    //코멘트 세이브하기
    @Transactional
    public Long createComment(Long postId, CommentRequestDto commentRequestDto) {
        //코멘트에 글 id, comment내용, 작성자 id 다 넣어주자
        //현재 로그인 된 멤버
        Long authorId = SecurityUtil.getCurrentMemberId();
        Member authorMember = memberRepository.findById(authorId).orElseThrow(()-> new NoAuthorityException("로그인 후 이용해주세요.")
        );
        //작성된 글
        Post post = postRepsitory.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        Comment comment1 = new Comment(authorMember, commentRequestDto, post);
        commentRepository.save(comment1);
        return postId;
    }

    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        //댓글의 작성자만 수정할 수 있으므로 댓글의 memberId 꺼내서 비교해보자
        Comment comment1 = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (comment1.getMember().getId() == SecurityUtil.getCurrentMemberId()) {
            comment1.setComment(commentRequestDto.getComment());
        }else{
            throw new NoAuthorityException("수정 권한이 없습니다.");
        }
        return commentId;
    }

    @Transactional
    public Long deleteComment(Long commentId) {
        //댓글의 작성자만 삭제하도록
        Comment comment1 = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("해당댓글없음"));
        if (comment1.getMember().getId() == SecurityUtil.getCurrentMemberId()) {
            commentRepository.delete(comment1);
        }else{
            throw new NoAuthorityException("삭제 권한이 없습니다.");
        }
        return commentId;
    }
}
