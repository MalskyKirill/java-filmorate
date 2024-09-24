package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ServerErrorException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.db.Film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.Film.FilmDbStorageImpl;
import ru.yandex.practicum.filmorate.storage.db.User.UserDbStorageImpl;
import ru.yandex.practicum.filmorate.storage.local.imp.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.local.imp.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorageImpl filmStorage;
    private final MpaServiceImpl mpaService;

    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film createFilm(Film film) {
//        if (filmStorage.isFilmNameContainedInBd(film)) {
            validationFilm(film);
            Film newFilm = filmStorage.createFilm(film);
            newFilm.setMpa(mpaService.getMpaById(newFilm.getMpa().getId()));
            filmStorage.addGenres(newFilm.getId(), film.getGenres());
            newFilm.setGenres(filmStorage.getGenres(newFilm.getId()));
            return newFilm;
//        }
//
//        throw new ServerErrorException("Фильм с таким названием уже существует");
    }

    @Override
    public Film getFilmById(Long id) {
        if (filmStorage.isFilmIdContainedInBd(id)) {
            return filmStorage.getFilmByID(id);
        }

        throw new NotFoundException(String.format(NotFoundException.FILM_NOT_FOUND, id));
    }

    @Override
    public Film updateFilm(Film newFilm) {
            validationFilm(newFilm);
            Film film = filmStorage.updateFilm(newFilm);
            film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
            filmStorage.updateGenres(film.getId(), newFilm.getGenres());
            film.setGenres(filmStorage.getGenres(film.getId()));
            return film;
    }

    @Override
    public void addLike(Long filmId, Long userId) {

    }

    @Override
    public void removeLike(Long filmId, Long userId) {

    }

    @Override
    public List<Film> showTopMovies(Integer lim) {
        return null;
    }

//    @Override
//    public Film createFilm(Film film) {
//        validationFilm(film); // валидируем пришедший фильм
//        return filmStorage.createFilm(film);
//    }
//
//    @Override
//    public Film updateFilm(Film newFilm) {
//        if (newFilm.getId() == null) { // проверка что фильм пришел с id
//            log.error("error - Id должен быть указан");
//            throw new NotFoundException("Id должен быть указан");
//        }
//
//        validationFilm(newFilm); // валидируем
//        return filmStorage.updateFilm(newFilm);
//    }
//
//    @Override
//    public void addLike(Long filmId, Long userId) {
//        Film film = filmStorage.getFilmByID(filmId); // получили фильм
//
//        userStorage.getUserById(userId); // проверили есть ли юзер
////        film.addLike(userId);
//        log.info("'юзер с id {}' лайкнул фильм '{}'", userId, filmId);
//    }
//
//    @Override
//    public void removeLike(Long filmId, Long userId) {
//        Film film = filmStorage.getFilmByID(filmId); // получили фильм
//
//        userStorage.getUserById(userId); // проверили есть ли юзер
////        film.removeLike(userId);
//        log.info("'юзер с id {}' снял лайк с фильма '{}'", userId, filmId);
//    }
//
//    @Override
//    public List<Film> showTopMovies(Integer lim) {
//        log.info("сортировка фильмов по кол-ву лайков");
//        return filmStorage.getAllFilms().stream()
////            .sorted(Comparator.comparing(Film::getLikeListSize).reversed())
//            .limit(lim)
//            .collect(Collectors.toList());
//    }

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
