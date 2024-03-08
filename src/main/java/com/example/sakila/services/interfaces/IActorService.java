package com.example.sakila.services.interfaces;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.partials.PartialFilm;
import com.example.sakila.input.ActorInput;

import java.util.List;
import java.util.Optional;

public interface IActorService {
    List<Actor> getActors();
    Actor getActorById(Short id);
    Optional<Actor> getActorByName(String firstName, String lastName);
    Actor createActor(ActorInput data);
    Actor updateActor(Short id, ActorInput data);
    void deleteActor(Short id);
    List<PartialFilm> getFilmsForActor(Short id);
    Actor addFilmsForActor(Short id, Short[] filmIds);
}
