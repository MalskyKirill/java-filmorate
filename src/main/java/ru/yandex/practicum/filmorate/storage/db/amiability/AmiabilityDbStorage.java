package ru.yandex.practicum.filmorate.storage.db.amiability;

import ru.yandex.practicum.filmorate.model.Amiability;

public interface AmiabilityDbStorage {
    void addFriend(Long userId, Long friendId, boolean status);

    void deleteFriend(Long userId, Long friendId);

    boolean isAmiability(Long userId, Long friendId);

    Amiability getAmiability(Long userId, Long friendId);
}
