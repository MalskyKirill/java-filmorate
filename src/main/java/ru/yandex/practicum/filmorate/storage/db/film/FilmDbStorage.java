package ru.yandex.practicum.filmorate.storage.db.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface FilmDbStorage  {
    List<Film> getAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film newFilm);

    Film getFilmByID(Long id);

    void addGenres(Long filmId, Set<Genre> genres);

    Set<Genre> getGenres(Long id);

    void deleteGenres(Long id);

    void updateGenres(Long filmId, Set<Genre> genres);
}
