package ru.yandex.practicum.filmorate.storage.db.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
@Slf4j
@RequiredArgsConstructor
public class MpaDbStorageImpl implements MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_RATING_MPA_BY_ID_QUERY = "SELECT * FROM rating_mpa WHERE mpa_id = ?";
    @Override
    public Mpa getRatingMpaById(Integer id) {
        Mpa mpa = jdbcTemplate.queryForObject(FIND_RATING_MPA_BY_ID_QUERY, new MpaRowMapper(), id);
        return mpa;
    }

    @Override
    public boolean isMpaIdContainedInBd(Integer id) {
        try {
            getRatingMpaById(id);
            log.trace("МПА id {} найден", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.trace("МПА id {} не найден", id);
            return false;
        }
    }
}
