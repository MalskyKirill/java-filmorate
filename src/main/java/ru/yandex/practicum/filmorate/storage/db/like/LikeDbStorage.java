package ru.yandex.practicum.filmorate.storage.db.like;

public interface LikeDbStorage {
    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    boolean isLikeContainsInBd(Long filmId, Long userId);

    int countLikes(Long filmId);
}
