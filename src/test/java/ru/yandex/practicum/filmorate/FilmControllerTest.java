package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storage.local.imp.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.local.imp.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

public class FilmControllerTest {
//    private InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
//    private InMemoryUserStorage userStorage = new InMemoryUserStorage();
//    private FilmService filmService = new FilmServiceImpl(filmStorage, userStorage);
//    private FilmController filmController = new FilmController(filmService);
//    private Film testFilm = Film.builder()
//        .id(1L)
//        .name("Java")
//        .description("Spring boot")
//        .releaseDate(LocalDate.of(1995, 05, 23))
//        .duration(1000)
//        .likes(new HashSet<>())
//        .build();
//
//    @Test
//    void createFilm() {
//        Film newFilm = new Film(1L, "Java", "Spring boot", LocalDate.of(1995, 05, 23), 1000, new HashSet<>());
//
//        filmController.createFilm(testFilm);
//        Assertions.assertEquals(1, filmController.getAllFilms().size());
//    }
//
//    @Test
//    void createUpdateAndAddFilm() {
//        Film newFilm = new Film(1L, "JavaScript", "Spring boot", LocalDate.of(1985, 05, 23), 1000, new HashSet<>());
//
//        filmController.createFilm(testFilm);
//        filmController.updateFilm(newFilm);
//        Assertions.assertEquals("JavaScript", newFilm.getName());
//    }
//
//    @Test
//    void createFilmWithNegativeDuration() {
//        Film newFilm = new Film(1L, "Java", "Spring boot", LocalDate.of(1985, 05, 23), -15, new HashSet<>());
//
//        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(newFilm));
//        Assertions.assertEquals(0, filmController.getAllFilms().size());
//    }
//
//    @Test
//    void createFilmWithDateReleaseBefore1895() {
//        Film newFilm = new Film(1L, "Java", "Spring boot", LocalDate.of(1885, 05, 23), 1000, new HashSet<>());
//
//        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(newFilm));
//        Assertions.assertEquals(0, filmController.getAllFilms().size());
//    }


}
