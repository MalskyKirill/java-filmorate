package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film createFilm(Film film) {
        validationFilm(film); // валидируем пришедший фильм
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (newFilm.getId() == null) { // проверка что фильм пришел с id
            log.error("error - Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }

        validationFilm(newFilm); // валидируем
        return filmStorage.updateFilm(newFilm);
    }

    private void validationFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("error - название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("error - максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("error - дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.error("error - продолжительность фильма должна быть положительным числом");
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }
    }
}
