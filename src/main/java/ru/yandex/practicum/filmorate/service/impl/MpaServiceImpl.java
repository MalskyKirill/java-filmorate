package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.db.RatingMpa.MpaRowMapper;

@Service
@RequiredArgsConstructor
public class mpaServiceImpl implements MpaService {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Mpa getMpaById(Integer id) {
        Mpa mpa = jdbcTemplate.queryForObject("SELECT * FROM rating_mpa WHERE mpa_id=?",
            new MpaRowMapper(), id);
        return mpa;
    }
}
