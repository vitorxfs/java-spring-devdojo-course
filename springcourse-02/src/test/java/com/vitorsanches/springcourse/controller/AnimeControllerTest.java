package com.vitorsanches.springcourse.controller;

import com.vitorsanches.springcourse.domain.Anime;
import com.vitorsanches.springcourse.dto.AnimeCreateDTO;
import com.vitorsanches.springcourse.dto.AnimeReplaceDTO;
import com.vitorsanches.springcourse.service.AnimeService;
import com.vitorsanches.springcourse.util.AnimeCreateDTOCreator;
import com.vitorsanches.springcourse.util.AnimeCreator;
import com.vitorsanches.springcourse.util.AnimeReplaceDTOCreator;
import com.vitorsanches.springcourse.util.DateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks // When you need to test the class itself
    private AnimeController animeController;

    @Mock // The dependencies
    private AnimeService animeServiceMock;
    @Mock
    private DateUtil dateUtilMock;


    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(dateUtilMock.formatLocalDateTimeToDatabaseStyle(ArgumentMatchers.any()))
                .thenReturn("2024-10-01 12:00:00");
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
        BDDMockito.when(animeServiceMock.listAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.findByIdOrThrow(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimeCreateDTO.class)) )
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimeReplaceDTO.class));
        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }


    @Test
    @DisplayName("list should return list of animes inside Page Object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll should return list of all animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = animeController.listAll().getBody();

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById should return anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeController.findById(expectedId).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName should return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = animeController.findByName(expectedName).getBody();

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName should return empty list of anime when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animeList = animeController.findByName("name").getBody();

        Assertions.assertThat(animeList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save should return anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimeCreateDTO animeDto = AnimeCreateDTOCreator.createValidAnimeDTO();
        Anime anime = animeController.save(animeDto).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace should return no content when successful")
    void replace_ReturnsNoContent_WhenSuccessful() {
        AnimeReplaceDTO animeDto = AnimeReplaceDTOCreator.createValidAnimeDTO();
        HttpStatusCode statusCode = animeController.replace(animeDto).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete should return no content when successful")
    void delete_ReturnsNoContent_WhenSuccessful() {
        HttpStatusCode statusCode = animeController.delete(1L).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT);
    }
}