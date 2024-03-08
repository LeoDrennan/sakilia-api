package com.example.sakila.services;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.partials.PartialFilm;
import com.example.sakila.input.ActorInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.services.interfaces.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ActorService implements IActorService {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    FilmRepository filmRepository;

    public ActorService(ActorRepository actorRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    public List<Actor> getActors() {

        return actorRepository.findAll();
    }

    public Actor getActorById(Short id) {

        return actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));
    }

    public Optional<Actor> getActorByName(String firstName, String lastName) {
        return actorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Actor createActor(ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());

        List<PartialFilm> films = new ArrayList<>();
        for (Short id : data.getFilmIds()) {
            Film film = filmRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

            films.add(new PartialFilm(film));
        }

        actor.setFilms(films);

        return actorRepository.save(actor);
    }

    public Actor updateActor(Short id, ActorInput data) {
        final var actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));

        if (data.getFirstName() != null) {
            actor.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null) {
            actor.setLastName(data.getLastName());
        }

        return actorRepository.save(actor);
    }

    public void deleteActor(Short id) {
        final boolean actorExists = actorRepository.existsById(id);

        if (actorExists) {
            actorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor.");
        }
    }

    public List<PartialFilm> getFilmsForActor(Short id) {
        final var actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));

        return actor.getFilms();
    }

    public Actor addFilmsForActor(Short id, Short[] filmIds) {
        final var actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));

        List<PartialFilm> films = actor.getFilms();
        for (Short filmId : filmIds) {
            Film film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));

            PartialFilm filmToAdd = new PartialFilm(film);

            // Add only if film is not already linked
            boolean isDuplicate = false;
            for (PartialFilm linkedFilm : films) {
                if (Objects.equals(linkedFilm.getId(), filmToAdd.getId())) {
                    isDuplicate = true;
                    break;
                }

            }

            if (!isDuplicate) {
                films.add(filmToAdd);
            }
        }

        actor.setFilms(films);

        return actorRepository.save(actor);
    }
}
