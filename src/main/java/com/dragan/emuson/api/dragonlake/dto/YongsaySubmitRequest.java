package com.dragan.emuson.api.dragonlake.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YongsaySubmitRequest {

    @NotEmpty
    private String content;

    @NotNull
    private Long categoryId;

}
