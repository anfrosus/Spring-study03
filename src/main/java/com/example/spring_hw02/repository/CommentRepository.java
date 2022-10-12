package com.example.spring_hw02.repository;

import com.example.spring_hw02.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findByMember_Id(Long memberId);

    void deleteAllByPostId(Long postId);
}
