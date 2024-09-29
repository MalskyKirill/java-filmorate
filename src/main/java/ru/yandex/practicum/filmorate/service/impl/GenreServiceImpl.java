package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.db.genre.GenreDbStorageImpl;

import java.util.List;

import static ru.yandex.practicum.filmorate.exceptions.ValidationException.GENRE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDbStorageImpl genreDbStorage;
    @Override
    public Genre getGenreById(Integer id) {
        if (isGenreIdContainedInBd(id)) {
            return genreDbStorage.getGenreById(id);
        }

        throw new NotFoundException(String.format(GENRE_NOT_FOUND, id));
    }

    @Override
    public boolean isGenreIdContainedInBd(Integer id) {
        return genreDbStorage.isGenreIdContainedInBd(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }
}
