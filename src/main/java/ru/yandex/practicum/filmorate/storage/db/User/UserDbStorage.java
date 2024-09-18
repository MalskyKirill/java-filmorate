package ru.yandex.practicum.filmorate.storage.db.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDbStorage {
    List<User> getUsers();

    User createUser(User user);

    User updateUser(User newUser);

    User getUserById(Long id);
}
