package com.dragan.emuson.api.mypage.repository;

import com.dragan.emuson.api.mypage.domain.Member;
import com.dragan.emuson.api.mypage.dto.ProfileUpdateRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.dragan.emuson.api.mypage.domain.QMember.member;

@Repository
public class MyPageRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MyPageRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    public Member findMemberById(Long id) {
        return em.find(Member.class, id);
    }

    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest) {
        queryFactory.update(member)
                .set(member.name, profileUpdateRequest.getName())
//                .set(member.email,profileUpdateRequest.getEmail())
                .set(member.imageUrl, profileUpdateRequest.getProfileImgUrl())
                .where(member.id.eq(userId))
                .execute();
    }
}
