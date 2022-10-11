package com.example.spring_hw02.repository;

import com.example.spring_hw02.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
