package com.vitorsanches.springcourse.service;

import com.vitorsanches.springcourse.domain.Anime;
import com.vitorsanches.springcourse.dto.AnimeCreateDTO;
import com.vitorsanches.springcourse.dto.AnimeReplaceDTO;
import com.vitorsanches.springcourse.exception.BadRequestException;
import com.vitorsanches.springcourse.mapper.AnimeMapper;
import com.vitorsanches.springcourse.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrow(Long id) {
        return animeRepository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public Anime save(AnimeCreateDTO animeDTO) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animeDTO));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrow(id));
    }

    public void replace(AnimeReplaceDTO animeDTO) {
        Anime savedAnime = findByIdOrThrow(animeDTO.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animeDTO);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }
}
