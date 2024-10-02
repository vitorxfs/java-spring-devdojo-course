package com.vitorsanches.springcourse.util;

import com.vitorsanches.springcourse.domain.Anime;
import com.vitorsanches.springcourse.dto.AnimeCreateDTO;

public class AnimeCreateDTOCreator {
    public static AnimeCreateDTO createValidAnimeDTO() {
        return AnimeCreateDTO.builder().name(AnimeCreator.createAnimeToBeSaved().getName()).build();
    }
}
