package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends RuntimeException {
    public static final String MPA_NOT_FOUND = "МПА с ID_%d не найден";
    public ValidationException(String message) {
        super(message);
    }
}
