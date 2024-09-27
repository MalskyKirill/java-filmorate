package ru.yandex.practicum.filmorate.storage.db.amiability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Amiability;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmiabilityDbStorageImpl implements AmiabilityDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_AMIABILITY_QUERY = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";
    private static final String FIND_AMIABILITY_QUERY = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String ADD_AMIABILITY_STATUS_TRUE_QUERY = "UPDATE friends SET status = true WHERE user_id = ? AND friend_id = ?";

    @Override
    public void addFriend(Long userId, Long friendId, boolean status) {
        jdbcTemplate.update(CREATE_AMIABILITY_QUERY, userId, friendId, status);
        Amiability amiability = getAmiability(userId, friendId);

        if (amiability.getStatus()) {
            jdbcTemplate.update(ADD_AMIABILITY_STATUS_TRUE_QUERY, friendId, userId);
            log.trace("Дружба между id {} и id {} ставла взаимной", userId, friendId);
        }

        log.trace("Добавлена связь: {}", amiability);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        Amiability amiability = getAmiability(userId, friendId);
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
        if (amiability.getStatus()) {
            jdbcTemplate.update("UPDATE friends SET status = false WHERE user_id = ? AND friend_id = ?", friendId, userId);
            log.trace("Дружба между id {} и id {} перестала быть взаимной", userId, friendId);
        }
        log.trace("Удалена связь: {}", amiability);
    }

    @Override
    public boolean isAmiability(Long userId, Long friendId) {
        try {
            getAmiability(userId, friendId);
            log.trace("Запрос на дружбу от пользователя id {} к пользователю id {} найден.",
                userId, friendId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.trace("Запрос на дружбу от пользователя id {} к пользователю id {} не найден.",
                userId, friendId);
            return false;
        }
    }

    @Override
    public Amiability getAmiability(Long userId, Long friendId) {
        Amiability res = jdbcTemplate.queryForObject(FIND_AMIABILITY_QUERY, new AmiabilityRowMapper(), userId, friendId);
        return res;
    }
}
