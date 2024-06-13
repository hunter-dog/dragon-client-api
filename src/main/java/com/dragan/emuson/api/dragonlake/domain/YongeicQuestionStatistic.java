package com.dragan.emuson.api.dragonlake.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class YongeicQuestionStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_statistic_id")
    private Long id;

    private Integer userQuizAnswer;

    private String userKeywordAnswer;

    private Integer selectedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private YongeicQuestion yongeicQuestion;

    public void increaseSelectedCount() {
        selectedCount++;
    }

}
