package com.vitorsanches.springcourse.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimeCreateDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
}
