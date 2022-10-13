package com.example.spring_hw02.model;

import com.example.spring_hw02.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post", nullable = false)
    private Post post;


    public Comment(Member member, CommentRequestDto commentRequestDto, Post post) {
        this.member = member;
        this.comment = commentRequestDto.getComment();
        this.post = post;
    }
}
