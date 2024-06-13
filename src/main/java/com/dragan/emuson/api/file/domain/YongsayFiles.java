package com.dragan.emuson.api.file.domain;

import com.dragan.emuson.api.dragonlake.domain.Yongsay;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "yongsay_files")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class YongsayFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yongsay_files_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yongsay_id")
    private Yongsay yongsay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_meta_id")
    private FileMeta fileMeta;


}