package com.dragan.emuson.api.dragonlake.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String writer;

    private String password;

    private String ip;

    private int depth;

    private int seq;

    private String content;

    private int likes;

    private int dislikes;

    private String avatarUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private GuestBook parent;

    @OneToMany(mappedBy = "parent")
    private List<GuestBook> children;

    public void addLikes() {
        this.likes += 1;
    }

    public void addDislikes() {
        this.dislikes += 1;
    }
}
