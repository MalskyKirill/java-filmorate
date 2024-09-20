package ru.yandex.practicum.filmorate.storage.db.Film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorageImpl implements FilmDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_FILMS_QUERY = "SELECT * FROM films";

    @Override
    public List<Film> getAllFilms() {
        List<Film> films = jdbcTemplate.query(FIND_ALL_FILMS_QUERY, new FilmRowMapper());
        return films;
    }

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        return null;
    }

    @Override
    public Film getFilmByID(Long id) {
        return null;
    }
}
