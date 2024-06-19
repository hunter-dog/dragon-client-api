package com.dragan.emuson.api.mypage.service;

import com.dragan.emuson.api.mypage.domain.Member;
import com.dragan.emuson.api.mypage.dto.MemberResponse;
import com.dragan.emuson.api.mypage.dto.ProfileUpdateRequest;
import com.dragan.emuson.api.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MyPageRepository myPageRepository;

    public MemberResponse getUserProfile(Long userId) {
        Member member = myPageRepository.findMemberById(userId);

        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    @Transactional
    public void updateUserProfile(Long userId, ProfileUpdateRequest profileUpdateRequest) {
        myPageRepository.updateProfile(userId, profileUpdateRequest);
    }
}
