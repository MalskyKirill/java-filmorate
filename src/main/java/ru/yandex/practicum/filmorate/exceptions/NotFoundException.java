package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundException extends RuntimeException {
    public static final String USER_NOT_FOUND = "Юзер ID_%d не найден";
    public static final String FILM_NOT_FOUND = "Фильм ID_%d не найден";
    public NotFoundException(String message) {
        super(message);
    }
}
