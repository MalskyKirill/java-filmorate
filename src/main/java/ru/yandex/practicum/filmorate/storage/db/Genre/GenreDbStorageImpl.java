package ru.yandex.practicum.filmorate.storage.db.Genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
@RequiredArgsConstructor
public class GenreDbStorageImpl implements GenreDbStorage{
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE genre_id = ?";

    @Override
    public Genre getGenreById(Integer id) {
        Genre genre = jdbcTemplate.queryForObject(FIND_GENRE_BY_ID_QUERY, new GenreRowMapper(), id);
        return genre;


    }
}
