package com.example.sakila.services.interfaces;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.partials.PartialActor;
import com.example.sakila.input.FilmInput;

import java.util.List;

public interface IFilmService {
    List<Film> getFilms();
    Film getFilmById(Short id);
    Film createFilm(FilmInput data);
    Film updateFilm(Short id, FilmInput data);
    void deleteFilm(Short id);
    List<PartialActor> getActorsForFilm(Short id);
    Film addActorsForFilm(Short id, Short[] actorIds);
}
