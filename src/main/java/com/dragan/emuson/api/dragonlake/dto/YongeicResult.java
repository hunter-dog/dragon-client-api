package com.dragan.emuson.api.dragonlake.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class YongeicResult {

    private Integer scoreAcquired;

    private int scorePerfect;

    private Double scoreAverage;

    private String challenger;

}
