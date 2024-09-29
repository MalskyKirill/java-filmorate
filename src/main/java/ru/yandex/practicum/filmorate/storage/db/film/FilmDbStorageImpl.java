package ru.yandex.practicum.filmorate.storage.db.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.genre.GenreRowMapper;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorageImpl implements FilmDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_FILMS_QUERY = "SELECT * FROM films";
    private static final String CREATE_FILM_QUERY = "INSERT INTO films (name, description, release_date , duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_FILM_BY_NAME_QUERY = "SELECT * FROM films WHERE name = ?";
    private static final String FIND_FILM_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String UPDATE_FILM_BY_ID_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";
    private static final String ADD_FILM_AND_GENRE_IN_FILM_GENRES_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String FIND_GENRES_QUERY = "SELECT f.genre_id, g.genre FROM film_genres AS f LEFT OUTER JOIN genres AS g ON f.genre_id = g.genre_id WHERE f.film_id = ? ORDER BY f.genre_id";
    private static final String DELETE_GENRES_FROM_FILM_QUERY = "DELETE FROM film_genres WHERE film_id = ?";


    @Override
    public List<Film> getAllFilms() {
        List<Film> films = jdbcTemplate.query(FIND_ALL_FILMS_QUERY, new FilmRowMapper());
        return films;
    }

    @Override
    public Film createFilm(Film film) {
        jdbcTemplate.update(CREATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());

        Film newFilm = jdbcTemplate.queryForObject(FIND_FILM_BY_NAME_QUERY, new FilmRowMapper(), film.getName());
        log.trace("Фильм {} создан в БД", newFilm.getId());
        return newFilm;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        jdbcTemplate.update(UPDATE_FILM_BY_ID_QUERY, newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(), newFilm.getDuration(), newFilm.getMpa().getId(), newFilm.getId());
        Film film = getFilmByID(newFilm.getId());
        log.trace("Фильм {} обновлен в БД", newFilm.getId());
        return film;
    }

    @Override
    public Film getFilmByID(Long id) {
        Film film = jdbcTemplate.queryForObject(FIND_FILM_BY_ID_QUERY, new FilmRowMapper(), id);
        log.trace("Фильм {} получен из БД", id);
        return film;
    }

    @Override
    public void addGenres(Long filmId, Set<Genre> genres) {
        for (Genre genre : genres) {
            jdbcTemplate.update(ADD_FILM_AND_GENRE_IN_FILM_GENRES_QUERY, filmId, genre.getId());
            log.trace("Фильму id {} добавлен жанр id {}.", filmId, genre.getId());
        }
    }

    @Override
    public Set<Genre> getGenres(Long id) {
        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(FIND_GENRES_QUERY, new GenreRowMapper(), id));
        log.trace("Найдены жанры для фильма id {}", id);
        return genres;
    }

    @Override
    public void deleteGenres(Long id) {
        jdbcTemplate.update(DELETE_GENRES_FROM_FILM_QUERY, id);
        log.trace("Жанры у фильма id {} удалены", id);
    }

    @Override
    public void updateGenres(Long filmId, Set<Genre> genres) {
        deleteGenres(filmId);
        System.out.println(genres);
        addGenres(filmId, genres);
    }

    public boolean isFilmIdContainedInBd(Long id) {
        try {
            getFilmByID(id);
            log.trace("Фильм с id = '{}' найден", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("error - Фильм с id = '{}' не найден", id);
            return false;
        }
    }


}
