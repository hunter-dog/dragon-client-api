package com.dragan.emuson.api.dragonlake.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class YongeicSubmitRequest {

    private Long examId;
    private List<YongeicUserAnswer> answers;

}
