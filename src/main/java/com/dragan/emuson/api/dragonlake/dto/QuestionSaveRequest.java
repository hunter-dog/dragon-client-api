package com.dragan.emuson.api.dragonlake.dto;

import lombok.Getter;

@Getter
public class QuestionSaveRequest {

    private Integer score;
    private String type;
    private String title;
    private String content;
    private String choice;
    private Integer quizAnswer;
    private String keywordAnswer;

}
