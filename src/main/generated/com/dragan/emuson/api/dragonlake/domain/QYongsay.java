package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongsay is a Querydsl query type for Yongsay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongsay extends EntityPathBase<Yongsay> {

    private static final long serialVersionUID = -929100959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QYongsay yongsay = new QYongsay("yongsay");

    public final QCategory category;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> winCount = createNumber("winCount", Integer.class);

    public final ListPath<com.dragan.emuson.api.file.domain.YongsayFiles, com.dragan.emuson.api.file.domain.QYongsayFiles> yongsayFiles = this.<com.dragan.emuson.api.file.domain.YongsayFiles, com.dragan.emuson.api.file.domain.QYongsayFiles>createList("yongsayFiles", com.dragan.emuson.api.file.domain.YongsayFiles.class, com.dragan.emuson.api.file.domain.QYongsayFiles.class, PathInits.DIRECT2);

    public QYongsay(String variable) {
        this(Yongsay.class, forVariable(variable), INITS);
    }

    public QYongsay(Path<? extends Yongsay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QYongsay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QYongsay(PathMetadata metadata, PathInits inits) {
        this(Yongsay.class, metadata, inits);
    }

    public QYongsay(Class<? extends Yongsay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

