package com.dragan.emuson.api.dragonlake.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamQuestion {

    private Long id;
    private Integer score;
    private String type;
    private String title;
    private String content;

    private Map<String, Object> choice;

    private Integer quizAnswer;
    private String keywordAnswer;

}
