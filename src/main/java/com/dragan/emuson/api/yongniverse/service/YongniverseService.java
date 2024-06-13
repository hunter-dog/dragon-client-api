package com.dragan.emuson.api.yongniverse.service;

import com.dragan.emuson.api.yongniverse.dto.YongniverseResponse;
import com.dragan.emuson.api.yongniverse.repository.YongniverseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongniverseService {

    private final YongniverseRepository yongniverseRepository;

    public List<YongniverseResponse> getYongniverse() {
        return yongniverseRepository.findAllYongniverse().stream()
                .map((person) -> YongniverseResponse.builder()
                        .id(person.getId())
                        .name(person.getName())
                        .likes(person.getLikes())
                        .dislikes(person.getDislikes())
                        .speciality(person.getSpeciality())
                        .snsUrl(person.getSnsUrl())
                        .thumbnailUrl(person.getThumbnailUrl())
                        .logoUrl(person.getLogoUrl())
                        .isDragonTeam(person.getIsDragonTeam())
                        .build())
                .collect(Collectors.toList());
    }

    public List<YongniverseResponse> getYongniverseTeamDragon() {
        return yongniverseRepository.findYongniverseTeamDragon().stream()
                .map((person) -> YongniverseResponse.builder()
                        .id(person.getId())
                        .name(person.getName())
                        .likes(person.getLikes())
                        .dislikes(person.getDislikes())
                        .speciality(person.getSpeciality())
                        .snsUrl(person.getSnsUrl())
                        .thumbnailUrl(person.getThumbnailUrl())
                        .logoUrl(person.getLogoUrl())
                        .isDragonTeam(person.getIsDragonTeam())
                        .build())
                .collect(Collectors.toList());
    }
}
