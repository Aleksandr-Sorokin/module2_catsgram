package ru.yandex.practicum.catsgram.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(final String massage){
        super(massage);
    }
}