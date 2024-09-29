package ru.yandex.practicum.filmorate.exceptions;

public class AlreadyExistsException extends ServerErrorException {
    public static final String AMIABILITY_ALREADY_EXIST = "Пользователи ID_%d и ID_%d уже друзья";

    public AlreadyExistsException(String message) {
        super(message);
    }
}
