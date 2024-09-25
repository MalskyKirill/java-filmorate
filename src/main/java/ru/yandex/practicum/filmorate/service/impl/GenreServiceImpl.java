package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.db.genre.GenreDbStorageImpl;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDbStorageImpl genreDbStorage;
    @Override
    public Genre getGenreById(Integer id) {
        return genreDbStorage.getGenreById(id);
    }
}
