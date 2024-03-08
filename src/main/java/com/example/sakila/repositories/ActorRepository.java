package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Short> {
    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);
}
