package com.dragan.emuson.api.yongniverse.repository;

import com.dragan.emuson.api.yongniverse.domain.Yongniverse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.dragan.emuson.api.yongniverse.domain.QYongniverse.yongniverse;

@Repository
public class YongniverseRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public YongniverseRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    public List<Yongniverse> findAllYongniverse() {
        return queryFactory.selectFrom(yongniverse)
                .where(yongniverse.isDragonTeam.eq(false))
                .orderBy(yongniverse.isBoss.desc())
                .orderBy(yongniverse.likes.desc())
                .fetch();
    }

    public List<Yongniverse> findYongniverseTeamDragon() {
        return queryFactory.selectFrom(yongniverse)
                .where(yongniverse.isDragonTeam.eq(true))
                .orderBy(yongniverse.isBoss.desc())
                .orderBy(yongniverse.likes.desc())
                .fetch();
    }
}
