package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    @Autowired
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() { // метод получения всех фильмов
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) { // метод создания фильма
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) { // обновляем фильм
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("{filmId}/like/{userId}")
    public void removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("popular")
    public List<Film> showTopMovies(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.showTopMovies(count);
    }
}
