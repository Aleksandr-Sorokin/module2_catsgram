package ru.yandex.practicum.catsgram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(final String massage){
        super(massage);
    }
}
