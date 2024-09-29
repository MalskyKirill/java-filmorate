package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.db.film.FilmDbStorageImpl;
import ru.yandex.practicum.filmorate.storage.db.like.LikeDbStorageImpl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exceptions.ValidationException.GENRE_NOT_FOUND;
import static ru.yandex.practicum.filmorate.exceptions.ValidationException.MPA_NOT_FOUND;

@Slf4j
@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorageImpl filmStorage;
    private final MpaServiceImpl mpaService;
    private final LikeDbStorageImpl likeDbStorage;
    private final GenreServiceImpl genreService;

    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film createFilm(Film film) {
        validationFilm(film);
        Film newFilm = filmStorage.createFilm(film);
        newFilm.setMpa(mpaService.getMpaById(newFilm.getMpa().getId()));
        filmStorage.addGenres(newFilm.getId(), film.getGenres());
        newFilm.setGenres(filmStorage.getGenres(newFilm.getId()));
        return newFilm;
    }

    @Override
    public Film getFilmById(Long id) {
        if (filmStorage.isFilmIdContainedInBd(id)) {
            Film newFilm = filmStorage.getFilmByID(id);
            newFilm.setMpa(mpaService.getMpaById(newFilm.getMpa().getId()));
            newFilm.setGenres(filmStorage.getGenres(newFilm.getId()));
            return newFilm;
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
        if (!likeDbStorage.isLikeContainsInBd(filmId, userId)) {
            likeDbStorage.addLike(filmId, userId);
            return;
        }

        throw new AlreadyExistsException("Не удалось добавить лайк");
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        if (likeDbStorage.isLikeContainsInBd(filmId, userId)) {
            likeDbStorage.removeLike(filmId, userId);
            return;
        }

        throw new AlreadyExistsException("Не удалось удалить лайк");
    }

    @Override
    public List<Film> showTopMovies(Integer lim) {

        List<Film> topFilms = getAllFilms()
            .stream()
            .sorted((f1, f2) -> likesCompare(f2, f1))
            .limit(lim)
            .collect(Collectors.toList());
        log.trace("Получен топ фильмов {}.", topFilms);
        return topFilms;
    }

    private int likesCompare(Film firstFilm, Film secondFilm) {
        return Integer.compare(likeDbStorage.countLikes(firstFilm.getId()), likeDbStorage.countLikes(secondFilm.getId()));
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
        if(!mpaService.isMpaIdContainedInBd(film.getMpa().getId())) {
            log.error("error - мпа не найдено");
            throw new ValidationException(String.format(MPA_NOT_FOUND, film.getMpa().getId()));
        }

        for (Genre genre : film.getGenres()) {
            if (!genreService.isGenreIdContainedInBd(genre.getId())) {
                log.error("error - жанр {} не найден", genre.getName());
                throw new ValidationException(String.format(GENRE_NOT_FOUND, genre.getId()));
            }
        }
    }
}
