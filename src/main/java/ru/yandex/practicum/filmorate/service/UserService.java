package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getUsers();

    User createUser(User user);

    User updateUser(User newUser);

    User getUserById(Long id);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getFriendList(Long userId);

    List<User> getListOfCommonFriends(Long userId, Long friendId);
}
