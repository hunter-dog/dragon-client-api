package com.dragan.emuson.api.yongniverse.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QYongniverse is a Querydsl query type for Yongniverse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYongniverse extends EntityPathBase<Yongniverse> {

    private static final long serialVersionUID = -151534783L;

    public static final QYongniverse yongniverse = new QYongniverse("yongniverse");

    public final NumberPath<Integer> dislikes = createNumber("dislikes", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBoss = createBoolean("isBoss");

    public final BooleanPath isDragonTeam = createBoolean("isDragonTeam");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final StringPath logoUrl = createString("logoUrl");

    public final StringPath name = createString("name");

    public final StringPath snsUrl = createString("snsUrl");

    public final StringPath speciality = createString("speciality");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QYongniverse(String variable) {
        super(Yongniverse.class, forVariable(variable));
    }

    public QYongniverse(Path<? extends Yongniverse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QYongniverse(PathMetadata metadata) {
        super(Yongniverse.class, metadata);
    }

}

