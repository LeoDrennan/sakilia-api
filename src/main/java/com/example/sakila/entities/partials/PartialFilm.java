package com.example.sakila.entities.partials;

import com.example.sakila.entities.Film;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film")
@Getter
@Setter
public class PartialFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="film_id")
    private Short id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    public PartialFilm() {
    }

    public PartialFilm(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.description = film.getDescription();
    }
}
