package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongeicQuestion is a Querydsl query type for YongeicQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongeicQuestion extends EntityPathBase<YongeicQuestion> {

    private static final long serialVersionUID = 1452733115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QYongeicQuestion yongeicQuestion = new QYongeicQuestion("yongeicQuestion");

    public final StringPath choice = createString("choice");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keywordAnswer = createString("keywordAnswer");

    public final NumberPath<Integer> quizAnswer = createNumber("quizAnswer", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    public final QYongeic yongeic;

    public QYongeicQuestion(String variable) {
        this(YongeicQuestion.class, forVariable(variable), INITS);
    }

    public QYongeicQuestion(Path<? extends YongeicQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QYongeicQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QYongeicQuestion(PathMetadata metadata, PathInits inits) {
        this(YongeicQuestion.class, metadata, inits);
    }

    public QYongeicQuestion(Class<? extends YongeicQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.yongeic = inits.isInitialized("yongeic") ? new QYongeic(forProperty("yongeic")) : null;
    }

}

