package com.example.sakila.controllers;

import com.example.sakila.entities.Language;
import com.example.sakila.input.LanguageInput;
import com.example.sakila.services.interfaces.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguageController {
    @Autowired
    ILanguageService languageService;

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();

        return ResponseEntity.ok(languages);
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Short id) {
        Language language = languageService.getLanguageById(id);

        return ResponseEntity.ok(language);
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> createLanguage(@Validated @RequestBody LanguageInput data) {
        final var language = languageService.createLanguage(data);

        return ResponseEntity.ok(language);
    }

    @PatchMapping("/languages/{id}")
    public ResponseEntity<Language> updateLanguage(@PathVariable Short id, @RequestBody LanguageInput data) {
        final var updatedLanguage = languageService.updateLanguage(id, data);

        return ResponseEntity.ok(updatedLanguage);
    }

    @DeleteMapping("/languages/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Short id) {
        languageService.deleteLanguage(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
