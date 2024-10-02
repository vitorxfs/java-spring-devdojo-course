package com.vitorsanches.springcourse.util;

import com.vitorsanches.springcourse.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder().name("Hajime no Ipoo").build();
    }

    public static Anime createValidAnime() {
        return Anime.builder().name("Hajime no Ipoo").id(1L).build();
    }

    public static Anime createUpdatedAnime() {
        return Anime.builder().name("Hajime no Ipoo 2").id(1L).build();
    }
}
