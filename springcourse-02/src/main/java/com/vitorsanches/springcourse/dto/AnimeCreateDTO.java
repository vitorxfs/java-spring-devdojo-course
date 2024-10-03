package com.vitorsanches.springcourse.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeCreateDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
}
