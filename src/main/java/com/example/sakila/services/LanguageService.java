package com.example.sakila.services;

import com.example.sakila.entities.Language;
import com.example.sakila.input.LanguageInput;
import com.example.sakila.repositories.LanguageRepository;
import com.example.sakila.services.interfaces.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LanguageService implements ILanguageService {
    @Autowired
    LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {

        return languageRepository.findAll();
    }

    public Language getLanguageById(Short id) {

        return languageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such language."));
    }

    public Language createLanguage(LanguageInput data) {
        final var language = new Language();
        language.setName(data.getName());

        return languageRepository.save(language);
    }

    public Language updateLanguage(Short id, LanguageInput data) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such language."));

        if (data.getName() != null) {
            language.setName(data.getName());
        }

        return languageRepository.save(language);
    }

    public void deleteLanguage(Short id) {
        languageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such language."));

        languageRepository.deleteById(id);
    }
}
