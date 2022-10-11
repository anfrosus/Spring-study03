package com.example.spring_hw02.repository;

import com.example.spring_hw02.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepsitory extends JpaRepository<Post, Long> {
    List<Post> findByOrderByCreateAtDesc();
}
