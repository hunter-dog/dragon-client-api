package com.dragan.emuson.api.dragonlake.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuestBook is a Querydsl query type for GuestBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuestBook extends EntityPathBase<GuestBook> {

    private static final long serialVersionUID = 1474490566L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuestBook guestBook = new QGuestBook("guestBook");

    public final StringPath avatarUrl = createString("avatarUrl");

    public final ListPath<GuestBook, QGuestBook> children = this.<GuestBook, QGuestBook>createList("children", GuestBook.class, QGuestBook.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Integer> dislikes = createNumber("dislikes", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final QGuestBook parent;

    public final StringPath password = createString("password");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath writer = createString("writer");

    public QGuestBook(String variable) {
        this(GuestBook.class, forVariable(variable), INITS);
    }

    public QGuestBook(Path<? extends GuestBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuestBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuestBook(PathMetadata metadata, PathInits inits) {
        this(GuestBook.class, metadata, inits);
    }

    public QGuestBook(Class<? extends GuestBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QGuestBook(forProperty("parent"), inits.get("parent")) : null;
    }

}

