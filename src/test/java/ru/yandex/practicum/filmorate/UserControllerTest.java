package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
public class UserControllerTest {
    private UserController userController = new UserController();

    private User testUser = User.builder()
        .id(1L)
        .email("javascript@yandex.ru")
        .login("JavaScript")
        .name("JS")
        .birthday(LocalDate.of(1995, 12, 4))
        .build();

    @Test
    void createAndAddUser() {
        User newUser = new User(1L, "javascript@yandex.ru", "JavaScript", "JS", LocalDate.of(1995, 12, 4));

        userController.createUser(newUser);
        Assertions.assertEquals(testUser, newUser);
    }

    @Test
    void createUpdateAndAddUser() {
        User newUser = new User(1L, "javascript@yandex.ru", "React", "JS", LocalDate.of(1995, 12, 4));

        userController.createUser(testUser);
        userController.updateUser(newUser);
        Assertions.assertEquals("React", newUser.getLogin());
    }

    @Test
    void createUserWithEmptyLoginOrWithSpace() {
        User newUser = new User(1L, "javascript@yandex.ru", "J S", "JS", LocalDate.of(1995, 12, 4));

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
        Assertions.assertEquals(0, userController.getUsers().size());
    }

    @Test
    void createUserWithFutureBirthday() {
        User newUser = new User(1L, "javascript@yandex.ru", "JS", "JS", LocalDate.of(2995, 12, 4));

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
        Assertions.assertEquals(0, userController.getUsers().size());
    }
}
