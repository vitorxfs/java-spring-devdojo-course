package com.vitorsanches.springcourse.service;

import com.vitorsanches.springcourse.domain.Anime;
import com.vitorsanches.springcourse.dto.AnimeCreateDTO;
import com.vitorsanches.springcourse.dto.AnimeReplaceDTO;
import com.vitorsanches.springcourse.exception.BadRequestException;
import com.vitorsanches.springcourse.repository.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks // When you need to test the class itself
    private AnimeService animeService;

    @Mock // The dependencies
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)) )
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }


    @Test
    @DisplayName("listAll should return list of anime inside Page Object when successful")
    void listAll_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(0, 5));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll should return list of all anime when successful")
    void listAll_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = animeService.listAll();

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrow should return anime when successful")
    void findByIdOrThrow_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeService.findByIdOrThrow(expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrow should throw BadRequestException when anime was not found")
    void findByIdOrThrow_throwsBadRequestException_WhenAnimeNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions
                .assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrow(1L));
    }

    @Test
    @DisplayName("findByName should return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = animeService.findByName(expectedName);

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName should return empty list of anime when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animeList = animeService.findByName("name");

        Assertions.assertThat(animeList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save should return anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimeCreateDTO animeDto = AnimeCreateDTOCreator.createValidAnimeDTO();
        Anime anime = animeService.save(animeDto);

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace should not throw exception when successful")
    void replace_DoesNotThrowException_WhenSuccessful() {
        AnimeReplaceDTO animeDto = AnimeReplaceDTOCreator.createValidAnimeDTO();
        Assertions.assertThatCode(() -> animeService.replace(animeDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete should not throw exception when successful")
    void delete_DoesNotThrowException_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.delete(1L)).doesNotThrowAnyException();
    }
}