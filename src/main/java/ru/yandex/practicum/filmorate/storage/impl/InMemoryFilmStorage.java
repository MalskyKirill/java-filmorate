package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(getNextId()); // присваеваем id

        films.put(film.getId(), film); // складываем в мапу
        log.info("'{}' фильм был добавлен, его id '{}'", film.getName(), film.getId());
        return film; // если все ок возвращаем фильм
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (films.containsKey(newFilm.getId())) { // если в мапе есть ключ айдишник
            films.put(newFilm.getId(), newFilm); // заменяем фильм на новый
            log.info("'{}' фильм с id '{}' был обновлен", newFilm.getName(), newFilm.getId());
            return newFilm; // возвращаем новый фильм
        }

        log.error("error - Фильм с id = '{}' не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @Override
    public Film getFilmByID(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }

        log.error("error - Фильм с id = '{}' не найден", id);
        throw new NotFoundException("Фильм с id = " + id);
    }


    private long getNextId() { // метод генерации id
        long currentMaxId = films
            .keySet() // получаем коллекцию айдишников
            .stream() // преобразовываем ее в стрим
            .mapToLong(id -> id) // возвращаем LongStream
            .max() // находим макс
            .orElse(0); // если значение присутствует возвращаем его или возвращаем 0

        return ++currentMaxId;
    }
}
