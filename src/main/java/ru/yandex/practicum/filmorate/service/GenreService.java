package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenreService {
    Genre getGenreById(Integer id);

    boolean isGenreIdContainedInBd(Integer id);
}
