package com.dragan.emuson.api.dragonlake.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateLieksRequest {

    private Boolean isLike;
    private String tableName;
    private Long id;

    @Setter
    private String ip;

}
