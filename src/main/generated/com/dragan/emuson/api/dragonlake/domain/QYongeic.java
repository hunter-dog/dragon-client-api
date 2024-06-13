package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongeic is a Querydsl query type for Yongeic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongeic extends EntityPathBase<Yongeic> {

    private static final long serialVersionUID = -929114187L;

    public static final QYongeic yongeic = new QYongeic("yongeic");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath examName = createString("examName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final ListPath<YongeicQuestion, QYongeicQuestion> questions = this.<YongeicQuestion, QYongeicQuestion>createList("questions", YongeicQuestion.class, QYongeicQuestion.class, PathInits.DIRECT2);

    public final NumberPath<Integer> scorePerfect = createNumber("scorePerfect", Integer.class);

    public final NumberPath<Integer> totalQuestionCount = createNumber("totalQuestionCount", Integer.class);

    public final StringPath writer = createString("writer");

    public QYongeic(String variable) {
        super(Yongeic.class, forVariable(variable));
    }

    public QYongeic(Path<? extends Yongeic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QYongeic(PathMetadata metadata) {
        super(Yongeic.class, metadata);
    }

}

