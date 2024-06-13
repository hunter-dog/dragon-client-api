package com.dragan.emuson.api.dragonlake.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    private String ip;

    private String history;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "is_like")
    private boolean isLike;

    public void updateHistory(String history) {
        this.history = history;
    }

}
