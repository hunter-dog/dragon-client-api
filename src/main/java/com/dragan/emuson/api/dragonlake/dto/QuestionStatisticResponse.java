package com.dragan.emuson.api.dragonlake.dto;

import lombok.*;

@Getter
@Setter
public class QuestionStatisticResponse {

    private Integer quizAnswer;
    private String keywordAnswer;

    private Integer selectedCount;
    private Integer totalSelectedCount;
    private Double selectedRate;

}
