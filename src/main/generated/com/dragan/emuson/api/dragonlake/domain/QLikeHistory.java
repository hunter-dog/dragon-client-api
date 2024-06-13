package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikeHistory is a Querydsl query type for LikeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeHistory extends EntityPathBase<LikeHistory> {

    private static final long serialVersionUID = 1450164130L;

    public static final QLikeHistory likeHistory = new QLikeHistory("likeHistory");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath history = createString("history");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final BooleanPath isLike = createBoolean("isLike");

    public QLikeHistory(String variable) {
        super(LikeHistory.class, forVariable(variable));
    }

    public QLikeHistory(Path<? extends LikeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikeHistory(PathMetadata metadata) {
        super(LikeHistory.class, metadata);
    }

}

