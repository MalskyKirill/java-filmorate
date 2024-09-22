package ru.yandex.practicum.filmorate.storage.db.Film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.ServerErrorException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.User.UserRowMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorageImpl implements FilmDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_FILMS_QUERY = "SELECT * FROM films";
    private static final String CREATE_FILM_QUERY = "INSERT INTO films (name, description, release_date , duration) VALUES (?, ?, ?, ?)";
    private static final String FIND_FILM_BY_NAME_QUERY = "SELECT * FROM films WHERE name = ?";
    private static final String FIND_FILM_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String UPDATE_FILM_BY_ID_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ? WHERE id = ?";


    @Override
    public List<Film> getAllFilms() {
        List<Film> films = jdbcTemplate.query(FIND_ALL_FILMS_QUERY, new FilmRowMapper());
        return films;
    }

    @Override
    public Film createFilm(Film film) {
        jdbcTemplate.update(CREATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());

        Film newFilm = jdbcTemplate.queryForObject(FIND_FILM_BY_NAME_QUERY, new FilmRowMapper(), film.getName());
        return newFilm;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        jdbcTemplate.update(UPDATE_FILM_BY_ID_QUERY, newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(), newFilm.getDuration(), newFilm.getId());
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

    public boolean isFilmContainedInBd(Long id) {
        try {
            getFilmByID(id);
            log.trace("Фильм с id = '{}' найден", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("error - Фильм с id = '{}' не найден", id);
            return false;
        }
    }

    public boolean isFilmNameContainedInBd(Film film) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM films WHERE name = ?", new FilmRowMapper(), film.getName());
            log.trace("Фильм с email = '{}' найден", film.getName());
            return false;
        } catch (EmptyResultDataAccessException e) {
            log.error("error - Фильм с id = '{}' не найден", film.getName());
            return true;
        }
    }
}
