package ru.yandex.practicum.filmorate.storage.db.Film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmDbStorage  {
    List<Film> getAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film newFilm);

    Film getFilmByID(Long id);
}
