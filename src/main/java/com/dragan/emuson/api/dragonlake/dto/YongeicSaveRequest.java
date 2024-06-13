package com.dragan.emuson.api.dragonlake.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class YongeicSaveRequest {

    private List<QuestionSaveRequest> exam;
    private int scorePerfect;
    private String examName;
    private String writer;
    private String password;

}
