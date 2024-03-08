package com.example.sakila.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LanguageInput {
    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 20)
    private String name;
}
