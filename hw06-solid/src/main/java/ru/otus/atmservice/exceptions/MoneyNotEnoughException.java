package ru.otus.atmservice.exceptions;

public class MoneyNotEnoughException extends RuntimeException {
    public MoneyNotEnoughException(String message) {
        super(message);
    }
}
