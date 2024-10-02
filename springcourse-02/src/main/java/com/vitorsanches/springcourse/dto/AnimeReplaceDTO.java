package com.vitorsanches.springcourse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimeReplaceDTO {
    private Long id;
    private String name;
}
