package com.dragan.emuson.api.file.service;

import com.dragan.emuson.api.file.domain.FileMeta;
import com.dragan.emuson.api.file.dto.FileMetaRequest;
import com.dragan.emuson.api.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;

    public FileMeta saveFileMeta(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        String uniqueFileName = UUID.randomUUID() + "." + extension;
        long size = file.getSize();

        FileMetaRequest fileMetaRequest = FileMetaRequest.builder()
                .originalName(originalFilename)
                .size(size)
                .extension(extension)
                .uniqueName(uniqueFileName)
                .build();

        FileMeta fileMeta = FileMeta.builder()
                .originalName(fileMetaRequest.getOriginalName())
                .uniqueName(fileMetaRequest.getUniqueName())
                .size(fileMetaRequest.getSize())
                .extension(fileMetaRequest.getExtension())
                .build();

        return fileRepository.saveFile(fileMeta);
    }

}
