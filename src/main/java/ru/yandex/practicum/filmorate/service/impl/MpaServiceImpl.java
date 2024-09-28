package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.db.mpa.MpaDbStorageImpl;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaDbStorageImpl mpaDbStorage;
    @Override
    public Mpa getMpaById(Integer id) {
        return mpaDbStorage.getRatingMpaById(id);
    }
    @Override
    public boolean isMpaIdContainedInBd(Integer id) {
        return mpaDbStorage.isMpaIdContainedInBd(id);
    }
}
