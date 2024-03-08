package com.example.sakila.services;

import com.example.sakila.entities.*;
import com.example.sakila.entities.partials.PartialActor;
import com.example.sakila.entities.partials.PartialCategory;
import com.example.sakila.input.FilmInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.CategoryRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.repositories.LanguageRepository;
import com.example.sakila.services.interfaces.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FilmService implements IFilmService {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Short id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));
    }

    public Film createFilm(FilmInput data) {
        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setDescription(data.getDescription());
        film.setReleaseYear(data.getReleaseYear());
        film.setOriginalLanguageId(data.getOriginalLanguageId());
        film.setRentalDuration(data.getRentalDuration());
        film.setRentalRate(data.getRentalRate());
        film.setLength(data.getLength());
        film.setReplacementCost(data.getReplacementCost());


        // Handle adding language
        Language language = languageRepository.findById(data.getLanguageId().shortValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such language."));

        film.setLanguage(language);


        // Handle adding actors
        List<PartialActor> actors = new ArrayList<>();
        for (Short id : data.getActorIds()) {
            Actor actor = actorRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

            actors.add(new PartialActor(actor));
        }

        film.setActors(actors);

        // Handle adding categories
        List<PartialCategory> categories = new ArrayList<>();
        for (Short id : data.getCategoryIds()) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category."));

            categories.add(new PartialCategory(category));
        }

        film.setCategories(categories);

        return filmRepository.save(film);
    }

    public Film updateFilm(Short id, FilmInput data) {
        final var film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

        // If a language id is provided, look up language and set if it exists
        if (data.getLanguageId() != null) {
            Language language = languageRepository.findById(data.getLanguageId().shortValue())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such language."));

            film.setLanguage(language);
        }

        // If a list of category ids is provided, get the current list and add to it
        if (data.getCategoryIds() != null) {
            List<PartialCategory> existingCategories = film.getCategories();

            for (Short categoryId : data.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category."));

                PartialCategory categoryToAdd = new PartialCategory(category);

                // Add only if category is not already linked
                boolean isDuplicate = false;
                for (PartialCategory existingCategory : existingCategories) {
                    if (Objects.equals(existingCategory.getId(), categoryToAdd.getId())) {
                        isDuplicate = true;
                        break;
                    }

                }

                if (!isDuplicate) {
                    existingCategories.add(categoryToAdd);
                }
            }
        }

        if (data.getTitle() != null) {
            film.setTitle(data.getTitle());
        }
        if (data.getDescription() != null) {
            film.setDescription(data.getDescription());
        }
        if (data.getReleaseYear() != null) {
            film.setReleaseYear(data.getReleaseYear());
        }
        if (data.getOriginalLanguageId() != null) {
            film.setOriginalLanguageId(data.getOriginalLanguageId());
        }
        if (data.getRentalDuration() != null) {
            film.setRentalDuration(data.getRentalDuration());
        }
        if (data.getRentalRate() != null) {
            film.setRentalRate(data.getRentalRate());
        }
        if (data.getLength() != null) {
            film.setLength(data.getLength());
        }
        if (data.getReplacementCost() != null) {
            film.setReplacementCost(data.getReplacementCost());
        }

        return filmRepository.save(film);
    }

    public void deleteFilm(Short id) {
        final boolean filmExists = filmRepository.existsById(id);

        if (filmExists) {
            filmRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film.");
        }
    }

    public List<PartialActor> getActorsForFilm(Short id) {
        final var film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

        return film.getActors();
    }

    public Film addActorsForFilm(Short id, Short[] actorIds) {
        final var film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));

        List<PartialActor> actors = film.getActors();
        for (Short actorId : actorIds) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

            PartialActor actorToAdd = new PartialActor(actor);

            // Add only if actor is not already linked
            boolean isDuplicate = false;
            for (PartialActor linkedActor : actors) {
                if (Objects.equals(linkedActor.getId(), actorToAdd.getId())) {
                    isDuplicate = true;
                    break;
                }

            }

            if (!isDuplicate) {
                actors.add(actorToAdd);
            }
        }

        film.setActors(actors);

        return filmRepository.save(film);
    }
}
