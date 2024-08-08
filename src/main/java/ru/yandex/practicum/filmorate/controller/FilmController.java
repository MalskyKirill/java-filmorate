package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilms() { // метод получения всех фильмов
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) { // метод создания фильма
        validationFilm(film); // валидируем пришедший фильм
        film.setId(getNextId()); // присваеваем id

        films.put(film.getId(), film); // складываем в мапу
        log.info("'{}' фильм был добавлен, его id '{}'", film.getName(), film.getId());
        return film; // если все ок возвращаем фильм
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) { // обновляем фильм
        System.out.println(newFilm.toString());
        if (newFilm.getId() == null) { // проверка что фильм пришел с id
            log.error("error - Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }

        validationFilm(newFilm); // валидируем

        if (films.containsKey(newFilm.getId())) { // если в мапе есть ключ айдишник
            films.put(newFilm.getId(), newFilm); // заменяем фильм на новый
            log.info("'{}' фильм с id '{}' был обновлен", newFilm.getName(), newFilm.getId());
            return newFilm; // возвращаем новый фильм
        }

        log.error("error - Фильм с id = '{}' не найден", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private void validationFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("error - название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("error - максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("error - дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.error("error - продолжительность фильма должна быть положительным числом");
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }
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
