package com.example.sakila.controllers;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.partials.PartialFilm;
import com.example.sakila.input.ActorInput;
import com.example.sakila.input.NameSearch;
import com.example.sakila.services.interfaces.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {
    @Autowired
    IActorService actorService;

    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> getAllActors() {
        final var actors = actorService.getActors();

        return ResponseEntity.ok(actors);
    }

    @GetMapping("/actors/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Short id) {
        final var actor = actorService.getActorById(id);

        return ResponseEntity.ok(actor);
    }

    @PostMapping("/actors/by-name")
    public ResponseEntity<Actor> getActorByFullName(@Validated @RequestBody NameSearch request) {
        final var actor = actorService.getActorByName(request.getFirstName(), request.getLastName());

        if (actor.isPresent()) {
            return ResponseEntity.ok(actor.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("/actors")
    public ResponseEntity<Actor> createActor(@Validated @RequestBody ActorInput data) {
        final var actor = actorService.createActor(data);

        return ResponseEntity.ok(actor);
    }

    @PatchMapping("/actors/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Short id, @RequestBody ActorInput data) {
        final var updatedActor = actorService.updateActor(id, data);

        return ResponseEntity.ok(updatedActor);
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Short id) {
        actorService.deleteActor(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/actors/{id}/films")
    public ResponseEntity<List<PartialFilm>> getFilmsForActor(@PathVariable Short id) {
        final var films = actorService.getFilmsForActor(id);

        return ResponseEntity.ok(films);
    }

    @PatchMapping("/actors/{id}/add-films")
    public ResponseEntity<Actor> addFilmsForActor(@PathVariable Short id, @RequestBody Short[] filmIds) {
        final var actor = actorService.addFilmsForActor(id, filmIds);

        return ResponseEntity.ok(actor);
    }
}
