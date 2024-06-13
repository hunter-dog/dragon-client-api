package com.dragan.emuson.api.dragonlake.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class GuestBookResponse {

    private Long id;
    private String writer;
    private String ip;
    private Integer depth;
    private Integer seq;
    private Long parentId;
    private String content;
    private Integer likes;
    private Integer dislikes;
    private Long childCount;
    private String avatarUrl;
    private LocalDateTime createdAt;

}
