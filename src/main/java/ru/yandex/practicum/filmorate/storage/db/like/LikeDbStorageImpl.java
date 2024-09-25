package ru.yandex.practicum.filmorate.storage.db.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeDbStorageImpl implements LikeDbStorage{
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_LIKE_QUERY = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    private static final String REMOVE_LIKE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String FIND_LIKE_QUERY = "SELECT * FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String FIND_LIKES_COUNT_QUERY = "SELECT COUNT(*) FROM likes WHERE film_id = ?";

    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update(ADD_LIKE_QUERY, filmId, userId);
        log.trace("Юзер с id {} лайкнул фильм с id {}", userId, filmId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        jdbcTemplate.update(REMOVE_LIKE_QUERY, filmId, userId);
        log.trace("Юзер с id {} убрал лайк фильму с id {}", userId, filmId);
    }

    @Override
    public boolean isLikeContainsInBd(Long filmId, Long userId) {
        try {
            jdbcTemplate.queryForObject(FIND_LIKE_QUERY, new LikeRowMapper(), filmId, userId);
            log.trace("У фильма с id {} есть лайк юзера с id {}", filmId, userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("У фильма с id {} не найден лайк юзера с id {}", filmId, userId);
            return false;
        }
    }

    @Override
    public int countLikes(Long filmId) {
        Integer count = Objects.requireNonNull(jdbcTemplate.queryForObject(FIND_LIKES_COUNT_QUERY, Integer.class, filmId));
        log.trace("У фильма с id {} количество лайков {}", filmId, count);
        return count;
    }
}
