package com.dragan.emuson.api.dragonlake.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RandomYongsayResponse {

    private Long id;
    private String content;
    private String categoryName;
    private String fileName;

}
