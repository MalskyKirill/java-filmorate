package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ServerErrorException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.db.amiability.AmiabilityDbStorageImpl;
import ru.yandex.practicum.filmorate.storage.db.user.UserDbStorageImpl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDbStorageImpl userDbStorage;
    private final AmiabilityDbStorageImpl amiabilityDbStorage;

    @Override
    public List<User> getUsers() {
        return userDbStorage.getUsers();
    }

    @Override
    public User createUser(User user) {
        if (userDbStorage.isUserEmailContainedInBd(user)) {
            validateUser(user);
            return userDbStorage.createUser(user);
        }

        throw new ServerErrorException("Пользователь с таким email уже существует");
    }

    @Override
    public User getUserById(Long id) {
        if (userDbStorage.isUserContainedInBd(id)) {
            return userDbStorage.getUserById(id);
        }

        throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, id));
    }

    @Override
    public User updateUser(User newUser) {
        if (userDbStorage.isUserContainedInBd(newUser.getId())) {
            validateUser(newUser);
            return userDbStorage.updateUser(newUser);
        }

        throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, newUser.getId()));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        checkFriendToAdd(userId, friendId);
        boolean isAmiability = amiabilityDbStorage.isAmiability(friendId, userId);
        amiabilityDbStorage.addFriend(userId, friendId, isAmiability);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        checkFriendToRemove(userId, friendId);
        amiabilityDbStorage.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> getFriendList(Long userId) {
        if (!userDbStorage.isUserContainedInBd(userId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, userId));
        }

        List<User> friends = amiabilityDbStorage.getFriendsId(userId)
            .stream()
            .map(id -> userDbStorage.getUserById(id))
            .toList();

        log.trace("Получен список друзей у id {}.", userId);
        return friends;
    }

    @Override
    public List<User> getListOfCommonFriends(Long userId, Long friendId) {

        checkCommonFriends(userId, friendId);

        List<User> commonFriends = CollectionUtils.intersection(
            amiabilityDbStorage.getFriendsId(userId),
            amiabilityDbStorage.getFriendsId(friendId))
            .stream()
            .map(id -> userDbStorage.getUserById(id))
            .toList();

        log.trace("Получен список общих друзей у id {} и id {}", userId, friendId);

        return commonFriends;
    }

    private void checkFriendToAdd(long userId, long friendId) {
        if (!userDbStorage.isUserContainedInBd(userId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, userId));
        }
        if (!userDbStorage.isUserContainedInBd(friendId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, friendId));
        }
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Id пользователя и Id друга совподают");
        }
        if (amiabilityDbStorage.isAmiability(userId, friendId)) {
            throw new AlreadyExistsException(String.format(AlreadyExistsException.AMIABILITY_ALREADY_EXIST, userId, friendId));
        }
    }

    private void checkFriendToRemove(long userId, long friendId) {
        if (!userDbStorage.isUserContainedInBd(userId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, userId));
        }
        if (!userDbStorage.isUserContainedInBd(friendId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, friendId));
        }
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Id пользователя и Id друга совподают");
        }
        if (!amiabilityDbStorage.isAmiability(userId, friendId)) {
            throw new NotFoundException(String.format(NotFoundException.AMIABILITY_NOT_FOUND, userId, friendId));
        }
    }

    private void checkCommonFriends(Long userId, Long friendId) {
        if (!userDbStorage.isUserContainedInBd(userId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, userId));
        }
        if (!userDbStorage.isUserContainedInBd(friendId)) {
            throw new NotFoundException(String.format(NotFoundException.USER_NOT_FOUND, friendId));
        }
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Id пользователя и Id друга совподают");
        }
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


}
