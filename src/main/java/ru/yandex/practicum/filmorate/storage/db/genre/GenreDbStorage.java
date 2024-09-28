package ru.yandex.practicum.filmorate.storage.db.genre;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenreDbStorage {
    Genre getGenreById(Integer id);

    boolean isGenreIdContainedInBd(Integer id);
}
