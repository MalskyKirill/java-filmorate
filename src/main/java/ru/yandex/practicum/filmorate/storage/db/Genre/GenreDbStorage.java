package ru.yandex.practicum.filmorate.storage.db.Genre;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenreDbStorage {
    Genre getGenreById(Integer id);
}
