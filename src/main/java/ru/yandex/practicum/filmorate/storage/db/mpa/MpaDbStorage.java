package ru.yandex.practicum.filmorate.storage.db.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDbStorage {
    Mpa getRatingMpaById(Integer id);

    List<Mpa> getAllMpa();

    boolean isMpaIdContainedInBd(Integer id);
}
