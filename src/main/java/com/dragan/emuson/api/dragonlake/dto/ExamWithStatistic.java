package com.dragan.emuson.api.dragonlake.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor

public class ExamWithStatistic {

    private Long examId;
    private String writer;
    private LocalDateTime createdAt;
    private String examName;
    private Integer likes;
    private Integer totalQuestionCount;
    private Integer scorePerfect;
    private Integer sumScoreAcquired;
    private Double avgScoreAcquired;
    private Long countOfTestTaker;

}
