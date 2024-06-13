package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongeicQuestionStatistic is a Querydsl query type for YongeicQuestionStatistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongeicQuestionStatistic extends EntityPathBase<YongeicQuestionStatistic> {

    private static final long serialVersionUID = -1943828267L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QYongeicQuestionStatistic yongeicQuestionStatistic = new QYongeicQuestionStatistic("yongeicQuestionStatistic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> selectedCount = createNumber("selectedCount", Integer.class);

    public final StringPath userKeywordAnswer = createString("userKeywordAnswer");

    public final NumberPath<Integer> userQuizAnswer = createNumber("userQuizAnswer", Integer.class);

    public final QYongeicQuestion yongeicQuestion;

    public QYongeicQuestionStatistic(String variable) {
        this(YongeicQuestionStatistic.class, forVariable(variable), INITS);
    }

    public QYongeicQuestionStatistic(Path<? extends YongeicQuestionStatistic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QYongeicQuestionStatistic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QYongeicQuestionStatistic(PathMetadata metadata, PathInits inits) {
        this(YongeicQuestionStatistic.class, metadata, inits);
    }

    public QYongeicQuestionStatistic(Class<? extends YongeicQuestionStatistic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.yongeicQuestion = inits.isInitialized("yongeicQuestion") ? new QYongeicQuestion(forProperty("yongeicQuestion"), inits.get("yongeicQuestion")) : null;
    }

}

