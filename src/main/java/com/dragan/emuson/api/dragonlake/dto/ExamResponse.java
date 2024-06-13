package com.dragan.emuson.api.dragonlake.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamResponse {

    private Long examId;

    private String examName;

    private String writer;

    private Integer likes;

    private Integer totalQuestionCount;

    private List<ExamQuestion> questions;

}
