package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateUser(user);
        user.setId(getNextId());

        users.put(user.getId(), user);
        log.info("'{}' юзер был добавлен, его id '{}'", user.getName(), user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            log.error("error - Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }

        validateUser(newUser);

        if (users.containsKey(newUser.getId())) {
            users.put(newUser.getId(), newUser);
            log.info("'{}' юзер с id '{}' был обновлен", newUser.getName(), newUser.getId());
            return newUser;
        }

        log.error("error - Фильм с id = '{}' не найден", newUser.getId());
        throw new ValidationException("Юзер с id = " + newUser.getId() + " не найден");
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().matches(".*\\s.*")) {
            log.error("логин не может быть пустым и содержать пробелы");
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("юзеру установлено имя", user.getLogin());
            user.setName(user.getLogin());
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

    private long getNextId() { // метод генерации id
        long currentMaxId = users
            .keySet() // получаем коллекцию айдишников
            .stream() // преобразовываем ее в стрим
            .mapToLong(id -> id) // возвращаем LongStream
            .max() // находим макс
            .orElse(0); // если значение присутствует возвращаем его или возвращаем 0

        return ++currentMaxId;
    }
}
