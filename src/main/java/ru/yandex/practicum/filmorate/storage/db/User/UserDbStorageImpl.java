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

    @Override
    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query(FIND_ALL_QUERY, new UserRowMapper());

        return users;
    }

    @Override
    public User createUser(User user) {
        return null;
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
