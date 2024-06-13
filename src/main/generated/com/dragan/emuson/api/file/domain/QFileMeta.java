package com.dragan.emuson.api.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileMeta is a Querydsl query type for FileMeta
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileMeta extends EntityPathBase<FileMeta> {

    private static final long serialVersionUID = 1215591990L;

    public static final QFileMeta fileMeta = new QFileMeta("fileMeta");

    public final StringPath extension = createString("extension");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originalName = createString("originalName");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public final StringPath uniqueName = createString("uniqueName");

    public QFileMeta(String variable) {
        super(FileMeta.class, forVariable(variable));
    }

    public QFileMeta(Path<? extends FileMeta> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileMeta(PathMetadata metadata) {
        super(FileMeta.class, metadata);
    }

}

