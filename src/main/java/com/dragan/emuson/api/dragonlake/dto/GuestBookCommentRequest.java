package com.dragan.emuson.api.dragonlake.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GuestBookCommentRequest {

    private String writer;
    private String password;

    @Setter
    private String ip;

    private Integer depth;
    private Integer seq;
    private Long parentId;
    private String content;
    private Integer likes;
    private Integer dislikes;
    private String avatarUrl;

}
