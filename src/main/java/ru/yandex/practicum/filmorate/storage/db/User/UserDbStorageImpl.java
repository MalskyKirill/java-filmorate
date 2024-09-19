package ru.yandex.practicum.filmorate.storage.db.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query(FIND_ALL_QUERY, new UserRowMapper());

        return users;
    }

    @Override
    public User createUser(User user) {
        jdbcTemplate.update(CREATE_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());

        User newUser = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", new UserRowMapper(), user.getEmail());
        return newUser;
    }

    @Override
    public User updateUser(User newUser) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }
}
