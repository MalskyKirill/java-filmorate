package ru.yandex.practicum.filmorate.storage.local;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getUsers();

    User createUser(User user);

    User updateUser(User newUser);

    User getUserById(Long id);
}
