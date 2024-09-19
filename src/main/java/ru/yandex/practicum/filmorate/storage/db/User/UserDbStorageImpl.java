package ru.yandex.practicum.filmorate.storage.db.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbStorageImpl implements UserDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String CREATE_QUERY = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE users SET email = ? login = ? name = ? birthday = ? WHERE id = ?";

    @Override
    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query(FIND_ALL_QUERY, new UserRowMapper());
        log.trace("Получены все юзеры из БД");
        return users;
    }

    @Override
    public User createUser(User user) {
        jdbcTemplate.update(CREATE_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());

        User newUser = jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL_QUERY, new UserRowMapper()
            , user.getEmail());
        log.trace("Юзер {} создан в БД", newUser.getId());
        return newUser;
    }

    @Override
    public User updateUser(User newUser) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, newUser.getEmail(), newUser.getLogin()
            , newUser.getName(), newUser.getBirthday(), newUser.getId());

        User user = getUserById(newUser.getId());
        log.trace("Юзер {} обновлен в БД", newUser.getId());
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = jdbcTemplate.queryForObject(FIND_USER_BY_ID_QUERY, new UserRowMapper(), id);
        log.trace("Юзер {} получен из БД", id);
        return user;
    }

    public boolean isUserContainedInBd(Long id) {
        try {
            getUserById(id);
            log.trace("Юзер с id = '{}' найден", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("error - Юзер с id = '{}' не найден", id);
            return false;
        }
    }
}
