package com.example.sakila.input;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Data
public class FilmInput {

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 128)
    private String title;

    private String description;

    private Year releaseYear;

    @NotNull(groups = ValidationGroup.Create.class)
    private Byte languageId;

    private Byte originalLanguageId;

    @NotNull(groups = ValidationGroup.Create.class)
    private Byte rentalDuration;

    @NotNull(groups = ValidationGroup.Create.class)
    @Digits(integer = 4, fraction = 2)
    private BigDecimal rentalRate;

    private Short length;

    @NotNull(groups = ValidationGroup.Create.class)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal replacementCost;

    private List<Short> actorIds;

    private List<Short> categoryIds;
}
