package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundAmiabilityException extends NotFoundException {
    public static final String AMIABILITY_NOT_FOUND = "Связь между ID_%d и ID_%d не найдена";

    public NotFoundAmiabilityException(String message) {
        super(message);
    }
}
