package com.example.sakila.entities;

import com.example.sakila.entities.partials.PartialFilm;
import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="actor_id")
    private Short id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "actor_id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id")}
    )
    private List<PartialFilm> films = new ArrayList<>();

    public Actor() {
    }

    public Actor(Short id, String firstName, String lastName, List<PartialFilm> films) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.films = films;
    }
}
