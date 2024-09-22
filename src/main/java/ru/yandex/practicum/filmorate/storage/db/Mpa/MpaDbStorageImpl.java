package ru.yandex.practicum.filmorate.storage.db.Mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
@RequiredArgsConstructor
public class MpaDbStorageImpl implements MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_RATING_MPA_BY_ID_QUERY = "SELECT * FROM rating_mpa WHERE mpa_id = ?";
    @Override
    public Mpa getRatingMpaById(Integer id) {
        Mpa mpa = jdbcTemplate.queryForObject(FIND_RATING_MPA_BY_ID_QUERY, new MpaRowMapper(), id);
        return mpa;
    }
}
