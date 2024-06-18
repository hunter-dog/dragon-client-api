package com.dragan.emuson.api.yongniverse.controller;

import com.dragan.emuson.api.yongniverse.service.YongniverseService;
import com.dragan.emuson.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dragon/yongniverse")
public class YongniverseController {

    private final YongniverseService yongniverseService;

    @GetMapping
    public Response getYonginverse() {
        return Response.ofSuccess(yongniverseService.getYongniverse());
    }

    @GetMapping("/team/dragon")
    public Response getYongniverseTeamDragon() {
        return Response.ofSuccess(yongniverseService.getYongniverseTeamDragon());
    }

}
