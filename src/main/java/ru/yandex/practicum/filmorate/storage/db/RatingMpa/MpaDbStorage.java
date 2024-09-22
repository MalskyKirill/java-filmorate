package ru.yandex.practicum.filmorate.storage.db.RatingMpa;

import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaDbStorage {
    Mpa getRatingMpaById(Integer id);
}
