package ru.yandex.practicum.filmorate.storage.db.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenreDbStorageImpl implements GenreDbStorage{
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE genre_id = ?";

    @Override
    public Genre getGenreById(Integer id) {
        Genre genre = jdbcTemplate.queryForObject(FIND_GENRE_BY_ID_QUERY, new GenreRowMapper(), id);
        return genre;
    }

    @Override
    public boolean isGenreIdContainedInBd(Integer id) {
        try {
            getGenreById(id);
            log.trace("жанр id {} найден", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.trace("жанр id {} не найден", id);
            return false;
        }
    }
}
