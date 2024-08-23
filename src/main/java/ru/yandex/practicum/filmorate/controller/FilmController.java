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

    @GetMapping
    public Collection<Film> getAllFilms() { // метод получения всех фильмов
        return null;
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) { // метод создания фильма
        return null;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) { // обновляем фильм
        return null;
    }

}
