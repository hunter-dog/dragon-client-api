package com.dragan.emuson.api.mypage.controller;

import com.dragan.emuson.api.mypage.dto.ProfileUpdateRequest;
import com.dragan.emuson.api.mypage.service.MyPageService;
import com.dragan.emuson.common.Response;
import com.dragan.emuson.security.CurrentUser;
import com.dragan.emuson.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/user/profile")
    public Response getUserProfile(@CurrentUser UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();

        return Response.ofSuccess(myPageService.getUserProfile(userId));
    }

    @PatchMapping("/user/update/profile")
    public Response updateUserProfile(@CurrentUser UserPrincipal userPrincipal,
                                      @Validated @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        Long userId = userPrincipal.getId();

        myPageService.updateUserProfile(userId, profileUpdateRequest);
        return Response.ofSuccess("회원수정 성공!", null);
    }


}
