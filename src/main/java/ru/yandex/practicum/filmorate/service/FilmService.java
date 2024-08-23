package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film newFilm);
}
