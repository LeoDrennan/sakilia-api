package com.example.sakila.entities;

import com.example.sakila.entities.partials.PartialActor;
import com.example.sakila.entities.partials.PartialCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="film_id")
    private Short id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="release_year")
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name="language_id", nullable = false)
    private Language language;

    @Column(name="original_language_id")
    private Byte originalLanguageId;

    @Column(name="rental_duration")
    private Byte rentalDuration;

    @Column(name="rental_rate")
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Column(name="rating")
    private String rating;

    @Column(name="special_features")
    private String specialFeatures;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<PartialActor> actors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<PartialCategory> categories = new ArrayList<>();

    public Film() {
    }

    public Film(Short id) {
        this.id = id;
    }
}
