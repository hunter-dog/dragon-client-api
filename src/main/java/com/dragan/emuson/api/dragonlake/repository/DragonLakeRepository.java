package com.dragan.emuson.api.dragonlake.repository;

import com.dragan.emuson.api.dragonlake.domain.*;
import com.dragan.emuson.api.dragonlake.dto.ExamWithStatistic;
import com.dragan.emuson.api.dragonlake.dto.GuestBookResponse;
import com.dragan.emuson.api.file.domain.YongsayFiles;
import com.dragan.emuson.api.yongniverse.domain.Yongniverse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.dragan.emuson.api.dragonlake.domain.QCategory.category;
import static com.dragan.emuson.api.dragonlake.domain.QGuestBook.guestBook;
import static com.dragan.emuson.api.dragonlake.domain.QLikeHistory.likeHistory;
import static com.dragan.emuson.api.dragonlake.domain.QYongeic.yongeic;
import static com.dragan.emuson.api.dragonlake.domain.QYongeicQuestion.yongeicQuestion;
import static com.dragan.emuson.api.dragonlake.domain.QYongeicQuestionStatistic.yongeicQuestionStatistic;
import static com.dragan.emuson.api.dragonlake.domain.QYongeicStatistic.yongeicStatistic;
import static com.dragan.emuson.api.dragonlake.domain.QYongsay.yongsay;
import static com.dragan.emuson.api.file.domain.QFileMeta.fileMeta;
import static com.dragan.emuson.api.file.domain.QYongsayFiles.yongsayFiles;

@Repository
public class DragonLakeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DragonLakeRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    public Category findCategoryById(Long id) {
        return em.find(Category.class, id);
    }

    public void saveYongsay(Yongsay yongsay) {
        em.persist(yongsay);
    }

    public void saveYongsayFiles(YongsayFiles yongsayFiles) {
        em.persist(yongsayFiles);
    }

    public List<Category> findCategories() {
        return queryFactory.selectFrom(category)
                .orderBy(category.id.asc())
                .fetch();
    }

    public Yongsay findRandomYongsay(Long currentId) {
        while (true) {
            Yongsay randomYongsay = queryFactory.selectFrom(yongsay)
                    .leftJoin(yongsay.category, category).fetchJoin()
                    .leftJoin(yongsay.yongsayFiles, yongsayFiles).fetchJoin()
                    .leftJoin(yongsayFiles.fileMeta, fileMeta).fetchJoin()
                    .orderBy(NumberExpression.random().asc())
                    .fetchFirst();

            if (!randomYongsay.getId().equals(currentId)) {
                return randomYongsay;
            }
        }
    }


    public List<Yongsay> findRandomYongsays(int count) {
        return queryFactory.selectFrom(yongsay)
                .leftJoin(yongsay.category, category).fetchJoin()
                .leftJoin(yongsay.yongsayFiles, yongsayFiles).fetchJoin()
                .leftJoin(yongsayFiles.fileMeta, fileMeta).fetchJoin()
                .orderBy(NumberExpression.random().asc())
                .limit(count)
                .fetch();

    }

    public Yongeic saveExam(Yongeic newExam) {
        em.persist(newExam);
        return newExam;
    }

    public void saveQuestion(YongeicQuestion newQuestion) {
        em.persist(newQuestion);
    }

    public Yongeic findExamById(Long examId) {
        return queryFactory.selectFrom(yongeic)
                .join(yongeic.questions, yongeicQuestion).fetchJoin()
                .where(yongeic.id.eq(examId))
                .orderBy(yongeicQuestion.sortOrder.asc())
                .fetchOne();
    }

    public List<ExamWithStatistic> findAllExams() {

        NumberExpression<Double> avgScoreAcquiredRounded = Expressions.numberTemplate(Double.class,
                "ROUND({0}, 2)", yongeicStatistic.scoreAcquired.avg());

        return queryFactory
                .select(Projections.constructor(ExamWithStatistic.class,
                        yongeic.id,
                        yongeic.writer,
                        yongeic.createdAt,
                        yongeic.examName,
                        yongeic.likes,
                        yongeic.totalQuestionCount,
                        yongeic.scorePerfect,
                        yongeicStatistic.scoreAcquired.sum(),
                        avgScoreAcquiredRounded,
                        yongeicStatistic.id.count()
                ))
                .from(yongeic)
                .leftJoin(yongeicStatistic).on(yongeic.id.eq(yongeicStatistic.yongeic.id))
                .groupBy(yongeic.id)
                .fetch();
    }

    public List<YongeicQuestion> findYongeicAnswers(Long examId) {
        return queryFactory.selectFrom(yongeicQuestion)
                .where(yongeicQuestion.yongeic.id.eq(examId))
                .orderBy(yongeicQuestion.sortOrder.asc())
                .fetch();
    }

    public void saveExamResult(YongeicStatistic examResult) {
        em.persist(examResult);
    }

    public LikeHistory findLikeHistoryByIp(String ip, Boolean isLike) {
        // todo 240210: timeUtil 메소드로 빼기
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);

        return queryFactory.selectFrom(likeHistory)
                .where(likeHistory.ip.eq(ip)
                        .and(likeHistory.createdAt.between(startOfDay, endOfDay))
                        .and(likeHistory.isLike.eq(isLike)))
                .fetchOne();
    }

    public void saveLikeHistory(LikeHistory newLikeHistory) {
        em.persist(newLikeHistory);
    }

    public Yongniverse findYongniverseById(Long id) {
        return em.find(Yongniverse.class, id);
    }

    public Long findTotalCountOfYongsays() {
        return queryFactory.select(yongsay.count())
                .from(yongsay)
                .fetchOne();
    }

//    public List<GuestBookResponse> findTotalGuestBook() {
//        QGuestBook parent = new QGuestBook("parent");
//        QGuestBook child = new QGuestBook("child");
//
//        JPAQuery<Long> childCount = queryFactory.select(child.count())
//                .from(child)
//                .where(child.parent.id.eq(parent.id));
//
//        return queryFactory
//                .select(Projections.constructor(GuestBookResponse.class,
//                        parent.id,
//                        parent.writer,
//                        parent.ip,
//                        parent.depth,
//                        parent.seq,
//                        parent.parent.id,
//                        parent.content,
//                        parent.likes,
//                        parent.dislikes,
//                        childCount
//                ))
//                .from(parent)
//                .where(parent.depth.eq(1))
//                .orderBy(parent.seq.asc())
//                .fetch();
//    }


//    public List<GuestBookResponse> findTotalGuestBook(Pageable pageable) {
//
//        QGuestBook parent = new QGuestBook("parent");
//        QGuestBook child = new QGuestBook("child");
//
//        return queryFactory.select(Projections.constructor(GuestBookResponse.class,
//                        parent.id,
//                        parent.writer,
//                        parent.ip,
//                        parent.depth,
//                        parent.seq,
//                        Expressions.nullExpression(Long.class),
//                        parent.content,
//                        parent.likes,
//                        parent.dislikes,
//                        child.id.count(),
//                        parent.avatarUrl,
//                        parent.createdAt
//                ))
//                .from(parent)
//                .leftJoin(child)
//                .on(parent.id.eq(child.parent.id)).fetchJoin()
//                .where(parent.depth.eq(1))
//                .groupBy(parent.id)
//                .orderBy(parent.seq.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//    }

    public List<GuestBookResponse> findTotalGuestBook(Pageable pageable, String orderBy) {
        QGuestBook parent = new QGuestBook("parent");
        QGuestBook child = new QGuestBook("child");

        OrderSpecifier orderSpecifier = null;

        if (orderBy.equals("timeASC")) {
            orderSpecifier = parent.createdAt.asc();
        } else if (orderBy.equals("likesDESC")) {
            orderSpecifier = parent.likes.desc();
        } else {
            orderSpecifier = parent.createdAt.desc();
        }

        return queryFactory.select(Projections.constructor(GuestBookResponse.class,
                        parent.id,
                        parent.writer,
                        parent.ip,
                        parent.depth,
                        parent.seq,
                        Expressions.nullExpression(Long.class),
                        parent.content,
                        parent.likes,
                        parent.dislikes,
                        child.id.count(),
                        parent.avatarUrl,
                        parent.createdAt
                ))
                .from(parent)
                .leftJoin(child).on(parent.id.eq(child.parent.id)).fetchJoin()
                .where(parent.depth.eq(1))
                .groupBy(parent.id)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public JPAQuery<Long> getGuestBookCountQuery() {
        return queryFactory.select(guestBook.count())
                .from(guestBook)
                .where(guestBook.depth.eq(1));
    }

    public List<GuestBookResponse> findChildCommentOfGuestBook(Long parentId) {
        return queryFactory.select(Projections.constructor(GuestBookResponse.class,
                        guestBook.id,
                        guestBook.writer,
                        guestBook.ip,
                        guestBook.depth,
                        guestBook.seq,
                        guestBook.parent.id,
                        guestBook.content,
                        guestBook.likes,
                        guestBook.dislikes,
                        Expressions.constant(0L),
                        guestBook.avatarUrl,
                        guestBook.createdAt
                ))
                .from(guestBook)
                .where(guestBook.depth.eq(2)
                        .and(guestBook.parent.id.eq(parentId)))
                .orderBy(guestBook.createdAt.asc())
                .fetch();
    }

    public Integer findNextSeqInGuestBook(Integer depth) {
        // todo 240215: 동시성이슈 생각해야 되나 (240308: 애초에 seq라는 칼럼이 필요가 없을 거 같음)
        Integer currentSeq = queryFactory.select(guestBook.seq.max())
                .from(guestBook)
                .where(guestBook.depth.eq(depth))
                .fetchOne();

        return currentSeq + 1;
    }

    public GuestBook findCommentById(Long id) {
        return queryFactory.selectFrom(guestBook)
                .where(guestBook.id.eq(id))
                .fetchOne();
    }

    public void saveGuestBookComment(GuestBook newComment) {
        em.persist(newComment);
    }

    public Yongsay findYongsayById(Long yongsayId) {
        return queryFactory.selectFrom(yongsay)
                .where(yongsay.id.eq(yongsayId))
                .fetchOne();
    }

    public Integer findTotalWinningCountOfYongsays(Long yongsayId) {
        return queryFactory.select(yongsay.winCount.sum())
                .from(yongsay)
                .fetchOne();
    }

    public YongeicQuestionStatistic findYongeicQuestionStatisticByQuizAnswer(Long questionId, Integer quizAnswer) {
        return queryFactory.selectFrom(yongeicQuestionStatistic)
                .where(yongeicQuestionStatistic.yongeicQuestion.id.eq(questionId)
                        .and(yongeicQuestionStatistic.userQuizAnswer.eq(quizAnswer)))
                .fetchOne();
    }

    public YongeicQuestionStatistic findYongeicQuestionStatisticByKeywordAnswer(Long questionId, String keywordAnswer) {
        return queryFactory.selectFrom(yongeicQuestionStatistic)
                .where(yongeicQuestionStatistic.yongeicQuestion.id.eq(questionId)
                        .and(yongeicQuestionStatistic.userKeywordAnswer.eq(keywordAnswer)))
                .fetchOne();
    }

    public void saveYongeicQuestionStatistic(YongeicQuestionStatistic newStatistic) {
        em.persist(newStatistic);
    }

    public List<YongeicQuestionStatistic> findYongeicQuestionStatisticByQuestionId(Long questionId) {
        return queryFactory.selectFrom(yongeicQuestionStatistic)
                .join(yongeicQuestionStatistic.yongeicQuestion, yongeicQuestion).fetchJoin()
                .where(yongeicQuestionStatistic.yongeicQuestion.id.eq(questionId))
                .orderBy(yongeicQuestionStatistic.userQuizAnswer.asc())
                .fetch();
    }

    public Integer findTotalSelectedCountOfQuestion(Long questionId) {
        return queryFactory.select(yongeicQuestionStatistic.selectedCount.sum())
                .from(yongeicQuestionStatistic)
                .where(yongeicQuestionStatistic.yongeicQuestion.id.eq(questionId))
                .fetchOne();
    }

    public Double findAvgYongeicScore(Long examId) {
        NumberExpression<Double> avgScoreAcquiredRounded = Expressions.numberTemplate(Double.class,
                "ROUND({0}, 2)", yongeicStatistic.scoreAcquired.avg());

        return queryFactory.select(avgScoreAcquiredRounded)
                .from(yongeicStatistic)
                .groupBy(yongeicStatistic.yongeic.id)
                .having(yongeicStatistic.yongeic.id.eq(examId))
                .fetchOne();
    }

    public GuestBook findGuestBookById(Long id) {
        return queryFactory.selectFrom(guestBook)
                .where(guestBook.id.eq(id))
                .fetchOne();
    }
}
