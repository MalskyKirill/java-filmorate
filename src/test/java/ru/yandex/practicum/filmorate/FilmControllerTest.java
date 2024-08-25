//package ru.yandex.practicum.filmorate;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.yandex.practicum.filmorate.controller.FilmController;
//import ru.yandex.practicum.filmorate.exceptions.ValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.service.FilmService;
//import ru.yandex.practicum.filmorate.service.UserService;
//import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;
//import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;
//import ru.yandex.practicum.filmorate.storage.FilmStorage;
//import ru.yandex.practicum.filmorate.storage.UserStorage;
//import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
//import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//
//@SpringBootTest
//public class FilmControllerTest {
//    private InMemoryFilmStorage storage = new InMemoryFilmStorage();
//    private InMemoryUserStorage userStorage = new InMemoryUserStorage();
//    private UserService userService = new UserServiceImpl(userStorage);
//    private FilmService service = new FilmServiceImpl(storage, userStorage);
//    private FilmController filmController = new FilmController();
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
//    void createAndAddFilm() {
//        Film newFilm = new Film(1L, "Java", "Spring boot", LocalDate.of(1995, 05, 23), 1000, new HashSet<>());
//
//        filmController.createFilm(newFilm);
//        Assertions.assertEquals(testFilm, newFilm);
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
//}
