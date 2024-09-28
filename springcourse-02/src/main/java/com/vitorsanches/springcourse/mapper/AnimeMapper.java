package com.vitorsanches.springcourse.mapper;

import com.vitorsanches.springcourse.domain.Anime;
import com.vitorsanches.springcourse.dto.AnimeCreateDTO;
import com.vitorsanches.springcourse.dto.AnimeReplaceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    Anime toAnime(AnimeCreateDTO animeDTO);
    Anime toAnime(AnimeReplaceDTO animeDTO);
}
