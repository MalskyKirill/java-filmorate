package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final InMemoryUserStorage userStorage;

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User createUser(User user) {
        validateUser(user);
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User newUser) {
        if (newUser.getId() == null) {
            log.error("error - Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }

        validateUser(newUser);
        return userStorage.updateUser(newUser);
    }

    @Override
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public void addFriend(Long userId, Long friendId) { // добавлеие в друзья
        User user = userStorage.getUserById(userId); // получили юзера
        User friend = userStorage.getUserById(friendId); // получили друга
        System.out.println(user);
        System.out.println(friend);

        user.addFriend(friendId); // добавляем друзей в список
        friend.addFriend(userId);

        log.info("Пользователи с id '{}' и '{}' подужились", userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) { // удаление из друзей
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        user.removeFriend(friendId);
        friend.removeFriend(userId);

        log.info("Пользователи с id '{}' и '{}' больше не друзья", userId, friendId);
    }

    @Override
    public List<User> getFriendList(Long id) { // получение списка друзей
        User user = userStorage.getUserById(id); // находим пользователя

        Set<Long> friends = user.getFriends(); // получаем список id друзей

        if (friends.isEmpty()) { // если список пуст
            log.error("Пользователи с id '{}' совсем нет друзей", id); // логируем
            throw new NotFoundException("Лист друзей пуст"); // и кидаем исключение
        }

        return friends.stream() // создаем стрим
            .map(userId -> userStorage.getUserById(userId)) // для каждого id возвращаем друга
            .collect(Collectors.toList()); // собираем друзей в список
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
        if (user.getFriends() == null) { // если создан новый пользователь без друзей
            user.setFriends(new HashSet<>());
        }
    }


}
