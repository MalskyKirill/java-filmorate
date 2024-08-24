package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

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

    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmByID(filmId); // получили фильм

        userStorage.getUserById(userId); // проверили есть ли юзер
        film.addLike(userId);
        log.info("'юзер с id {}' лайкнул фильм '{}'", userId, filmId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmByID(filmId); // получили фильм

        userStorage.getUserById(userId); // проверили есть ли юзер
        film.removeLike(userId);
        log.info("'юзер с id {}' снял лайк с фильма '{}'", userId, filmId);
    }

    @Override
    public List<Film> showTopTenMovies() {
        log.info("сортировка фильмов по кол-ву лайков");
        return filmStorage.getAllFilms().stream()
            .sorted(Comparator.comparing(film -> film.getLikes().size()))
            .sorted(Collections.reverseOrder())
            .limit(10)
            .collect(Collectors.toList());
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
