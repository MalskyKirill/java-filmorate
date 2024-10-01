package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.db.mpa.MpaDbStorageImpl;

import java.util.List;
import static ru.yandex.practicum.filmorate.exceptions.ValidationException.MPA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaDbStorageImpl mpaDbStorage;

    @Override
    public Mpa getMpaById(Integer id) {
        if (isMpaIdContainedInBd(id)) {
            return mpaDbStorage.getRatingMpaById(id);
        }

        throw new NotFoundException(String.format(MPA_NOT_FOUND, id));
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAllMpa();
    }

    @Override
    public boolean isMpaIdContainedInBd(Integer id) {
        return mpaDbStorage.isMpaIdContainedInBd(id);
    }
}
