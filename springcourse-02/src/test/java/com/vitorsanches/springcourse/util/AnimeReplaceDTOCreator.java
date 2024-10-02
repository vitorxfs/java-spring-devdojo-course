package com.vitorsanches.springcourse.util;

import com.vitorsanches.springcourse.dto.AnimeReplaceDTO;

public class AnimeReplaceDTOCreator {
    public static AnimeReplaceDTO createValidAnimeDTO() {
        return AnimeReplaceDTO
                .builder()
                .name(AnimeCreator.createUpdatedAnime().getName())
                .id(AnimeCreator.createUpdatedAnime().getId())
                .build();
    }
}
