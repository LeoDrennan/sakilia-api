package com.example.sakila.controllers;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.partials.PartialActor;
import com.example.sakila.input.FilmInput;
import com.example.sakila.services.interfaces.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    @Autowired
    IFilmService filmService;

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getFilms() {
        List<Film> films = filmService.getFilms();

        return ResponseEntity.ok(films);
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Short id) {
        Film film = filmService.getFilmById(id);

        return ResponseEntity.ok(film);
    }

    @PostMapping("/films")
    public ResponseEntity<Film> createFilm(@Validated @RequestBody FilmInput data) {
        Film createdFilm = filmService.createFilm(data);

        return ResponseEntity.ok(createdFilm);
    }

    @PatchMapping("/films/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Short id, @RequestBody FilmInput data) {
        Film updatedFilm = filmService.updateFilm(id, data);

        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/films/{id}/actors")
    public ResponseEntity<List<PartialActor>> getActorsForFilm(@PathVariable Short id) {
        List<PartialActor> actors = filmService.getActorsForFilm(id);

        return ResponseEntity.ok(actors);
    }

    @PatchMapping("/films/{id}/add-actors")
    public ResponseEntity<Film> addActorsToFilm(@PathVariable Short id, @RequestBody Short[] actorIds) {
        final var film = filmService.addActorsForFilm(id, actorIds);

        return ResponseEntity.ok(film);
    }
}
