package ru.yandex.practicum.filmorate.storage.db.Mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaDbStorage {
    Mpa getRatingMpaById(Integer id);
}
