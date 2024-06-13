package com.dragan.emuson.api.file.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_meta")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FileMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_meta_id")
    private Long id;

    private String originalName;

    private String uniqueName;

    private Long size;

    private String extension;

}
