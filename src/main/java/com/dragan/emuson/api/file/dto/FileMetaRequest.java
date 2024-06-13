package com.dragan.emuson.api.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileMetaRequest {

    private String originalName;
    private String uniqueName;
    private Long size;
    private String extension;

}
