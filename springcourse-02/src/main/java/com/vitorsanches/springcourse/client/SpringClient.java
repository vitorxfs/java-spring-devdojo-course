package com.vitorsanches.springcourse.client;

import com.vitorsanches.springcourse.domain.Anime;
import jakarta.validation.constraints.Null;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                });
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);

        Anime anime = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> savedAnimeResponse = new RestTemplate().exchange(
                "http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<Anime>(anime, createJsonHeader()),
                Anime.class
        );
        log.info("saved anime {}", savedAnimeResponse);

        Anime animeToBeUpdated = savedAnimeResponse.getBody();
        if (animeToBeUpdated == null) { log.error("not found anime"); return; }
        animeToBeUpdated.setName("Samurai Champloo 2");
        ResponseEntity<Void> updated = new RestTemplate().exchange(
                "http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class
        );
        log.info(updated);

        ResponseEntity<Void> deleted = new RestTemplate().exchange(
                "http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class,
                animeToBeUpdated.getId()
        );
        log.info(updated);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
