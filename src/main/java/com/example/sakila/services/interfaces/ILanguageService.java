package com.example.sakila.services.interfaces;

import com.example.sakila.entities.Language;
import com.example.sakila.input.LanguageInput;

import java.util.List;

public interface ILanguageService {
    List<Language> getAllLanguages();
    Language getLanguageById(Short id);
    Language createLanguage(LanguageInput data);
    Language updateLanguage(Short id, LanguageInput data);
    void deleteLanguage(Short id);
}
