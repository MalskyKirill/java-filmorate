package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

public class UserControllerTest {
    private InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private UserServiceImpl userService = new UserServiceImpl(userStorage);
    private UserController userController = new UserController(userService);

    private User testUser = User.builder()
        .id(1L)
        .email("javascript@yandex.ru")
        .login("JavaScript")
        .name("JS")
        .birthday(LocalDate.of(1995, 12, 4))
        .build();

    @Test
    void createAndAddUser() {
        userController.createUser(testUser);
        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    void createUpdateAndAddUser() {
        User newUser = new User(1L, "javascript@yandex.ru", "React", "JS", LocalDate.of(1995, 12, 4), new HashSet<>());

        userController.createUser(testUser);
        userController.updateUser(newUser);
        Assertions.assertEquals("React", newUser.getLogin());
    }

    @Test
    void createUserWithEmptyLoginOrWithSpace() {
        User newUser = new User(1L, "javascript@yandex.ru", "J S", "JS", LocalDate.of(1995, 12, 4), new HashSet<>());

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
        Assertions.assertEquals(0, userController.getUsers().size());
    }

    @Test
    void createUserWithFutureBirthday() {
        User newUser = new User(1L, "javascript@yandex.ru", "JS", "JS", LocalDate.of(2995, 12, 4), new HashSet<>());

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
        Assertions.assertEquals(0, userController.getUsers().size());
    }
}
