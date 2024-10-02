package com.vitorsanches.springcourse.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimeCreateDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
}
