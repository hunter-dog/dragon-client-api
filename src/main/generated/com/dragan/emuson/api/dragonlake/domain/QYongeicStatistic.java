package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongeicStatistic is a Querydsl query type for YongeicStatistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongeicStatistic extends EntityPathBase<YongeicStatistic> {

    private static final long serialVersionUID = 1786027291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QYongeicStatistic yongeicStatistic = new QYongeicStatistic("yongeicStatistic");

    public final StringPath challenger = createString("challenger");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> scoreAcquired = createNumber("scoreAcquired", Integer.class);

    public final NumberPath<Integer> scorePerfect = createNumber("scorePerfect", Integer.class);

    public final QYongeic yongeic;

    public QYongeicStatistic(String variable) {
        this(YongeicStatistic.class, forVariable(variable), INITS);
    }

    public QYongeicStatistic(Path<? extends YongeicStatistic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QYongeicStatistic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QYongeicStatistic(PathMetadata metadata, PathInits inits) {
        this(YongeicStatistic.class, metadata, inits);
    }

    public QYongeicStatistic(Class<? extends YongeicStatistic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.yongeic = inits.isInitialized("yongeic") ? new QYongeic(forProperty("yongeic")) : null;
    }

}

