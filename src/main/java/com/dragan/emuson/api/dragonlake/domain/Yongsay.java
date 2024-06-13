package com.dragan.emuson.api.dragonlake.domain;

import com.dragan.emuson.api.file.domain.YongsayFiles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "yongsay")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Yongsay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yongsay_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    private Integer winCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "yongsay")
    private List<YongsayFiles> yongsayFiles;

    public void increaseWinCount() {
        this.winCount++;
    }

}
