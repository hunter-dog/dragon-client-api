package com.dragan.emuson.api.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QYongsayFiles is a Querydsl query type for YongsayFiles
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongsayFiles extends EntityPathBase<YongsayFiles> {

    private static final long serialVersionUID = 804944816L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QYongsayFiles yongsayFiles = new QYongsayFiles("yongsayFiles");

    public final QFileMeta fileMeta;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dragan.emuson.api.dragonlake.domain.QYongsay yongsay;

    public QYongsayFiles(String variable) {
        this(YongsayFiles.class, forVariable(variable), INITS);
    }

    public QYongsayFiles(Path<? extends YongsayFiles> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QYongsayFiles(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QYongsayFiles(PathMetadata metadata, PathInits inits) {
        this(YongsayFiles.class, metadata, inits);
    }

    public QYongsayFiles(Class<? extends YongsayFiles> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fileMeta = inits.isInitialized("fileMeta") ? new QFileMeta(forProperty("fileMeta")) : null;
        this.yongsay = inits.isInitialized("yongsay") ? new com.dragan.emuson.api.dragonlake.domain.QYongsay(forProperty("yongsay"), inits.get("yongsay")) : null;
    }

}

