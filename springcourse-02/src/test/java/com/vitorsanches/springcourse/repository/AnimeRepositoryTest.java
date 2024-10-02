package com.vitorsanches.springcourse.repository;

import com.vitorsanches.springcourse.domain.Anime;

import com.vitorsanches.springcourse.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("should create new anime")
    void save_PersistAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("should update existing anime")
    void save_UpdateAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        savedAnime.setName("Overlord");

        Anime updatedAnime = this.animeRepository.save(savedAnime);

        Assertions.assertThat(updatedAnime).isNotNull();
        Assertions.assertThat(updatedAnime.getId()).isNotNull();
        Assertions.assertThat(updatedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("should remove existing anime")
    void delete_RemoveAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        this.animeRepository.delete(savedAnime);
        Optional<Anime> animeById = this.animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(animeById).isEmpty();
    }

    @Test
    @DisplayName("should find existing anime by name")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        String name = savedAnime.getName();

        List<Anime> animesByName = this.animeRepository.findByName(name);

        Assertions.assertThat(animesByName).isNotEmpty().contains(savedAnime);
    }

    @Test
    @DisplayName("should return empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeNotFound() {
        List<Anime> animesByName = this.animeRepository.findByName("animeThatDoesNotExist");

        Assertions.assertThat(animesByName).isEmpty();
    }

    @Test
    @DisplayName("should throw ConstraintViolationException when saving with empty name ")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime)).isInstanceOf(ConstraintViolationException.class);
        // OR
        Assertions
                .assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("name cannot be empty");
    }


}