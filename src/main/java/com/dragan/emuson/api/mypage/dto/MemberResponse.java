package com.dragan.emuson.api.mypage.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String name;
    private String email;
    private String profileImgUrl;

}
