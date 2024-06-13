package com.dragan.emuson.api.yongniverse.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YongniverseResponse {

    private Long id;

    private String name;

    private boolean isBoss;

    private int likes;

    private int dislikes;

    private String speciality;

    private String snsUrl;

    private String thumbnailUrl;

    private String logoUrl;

    private boolean isDragonTeam;

}
