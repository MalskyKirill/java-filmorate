package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaService {
    Mpa getMpaById(Integer id);
    boolean isMpaIdContainedInBd(Integer id);
}
