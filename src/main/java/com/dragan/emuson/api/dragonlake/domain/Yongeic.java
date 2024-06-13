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
public class Yongeic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long id;

    private String writer;

    private LocalDateTime createdAt;

    private String examName;

    private Integer likes;

    private Integer totalQuestionCount;

    private Integer scorePerfect;

    @OneToMany(mappedBy = "yongeic")
    private List<YongeicQuestion> questions;

}
