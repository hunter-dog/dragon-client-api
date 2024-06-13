package com.dragan.emuson.api.file.repository;

import com.dragan.emuson.api.file.domain.FileMeta;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FileRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    public FileMeta saveFile(FileMeta fileMeta) {
        em.persist(fileMeta);
        return fileMeta;
    }

}
