package ru.yandex.practicum.filmorate.storage.local.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.local.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        user.setId(getNextId());

        users.put(user.getId(), user);
        log.info("'{}' юзер был добавлен, его id '{}'", user.getName(), user.getId());
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        if (users.containsKey(newUser.getId())) {
            users.put(newUser.getId(), newUser);
            log.info("'{}' юзер с id '{}' был обновлен", newUser.getName(), newUser.getId());
            return newUser;
        }

        log.error("error - Юзер с id = '{}' не найден", newUser.getId());
        throw new NotFoundException("Юзер с id = " + newUser.getId() + " не найден");
    }

    public User getUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }

        log.error("error - Юзер с id = '{}' не найден", id);
        throw new NotFoundException("Юзер с id = " + id);
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
