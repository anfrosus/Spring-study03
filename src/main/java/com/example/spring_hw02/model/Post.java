package com.example.spring_hw02.model;

import com.example.spring_hw02.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends TimeStamped {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    //컬럼으로 만들지는 않지만 ? 객체간의 관계
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    public Post(PostRequestDto postRequestDto, Member member) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.member = member;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

}
