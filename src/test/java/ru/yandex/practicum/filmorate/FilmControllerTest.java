package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTest {
    private FilmController filmController = new FilmController();

    private Film testFilm = Film.builder()
        .id(1L)
        .name("Java")
        .description("Spring boot")
        .releaseDate(LocalDate.of(1995, 05, 23))
        .duration(1000)
        .build();

    @Test
    void createAndAddFilm() {
        Film newFilm = new Film(1L, "Java", "Spring boot"
            , LocalDate.of(1985, 05, 23), 1000);

        filmController.createFilm(newFilm);
        Assertions.assertEquals(testFilm, newFilm);
    }

    @Test
    void createUpdateAndAddFilm() {
        Film newFilm = new Film(1L, "JavaScript", "Spring boot"
            , LocalDate.of(1985, 05, 23), 1000);

        filmController.createFilm(testFilm);
        filmController.updateFilm(newFilm);
        Assertions.assertEquals("JavaScript", newFilm.getName());
    }

    @Test
    void createFilmWithNegativeDuration() {
        Film newFilm = new Film(1L, "Java", "Spring boot"
            , LocalDate.of(1985, 05, 23), -15);

        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(newFilm));
        Assertions.assertEquals(0, filmController.getAllFilms().size());
    }

    @Test
    void createFilmWithDateReleaseBefore1895() {
        Film newFilm = new Film(1L, "Java", "Spring boot"
            , LocalDate.of(1885, 05, 23), 1000);

        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(newFilm));
        Assertions.assertEquals(0, filmController.getAllFilms().size());
    }
}
