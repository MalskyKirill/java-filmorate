package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.db.Mpa.MpaDbStorageImpl;
import ru.yandex.practicum.filmorate.storage.db.Mpa.MpaRowMapper;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaDbStorageImpl mpaDbStorage;
    @Override
    public Mpa getMpaById(Integer id) {
        return mpaDbStorage.getRatingMpaById(id);
    }
}
