package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.user.User;

import java.util.*;

@RestController
public class UserController {
private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<String, User> users = new HashMap();

    @GetMapping("/users")
    public Map<String, User> allUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    @PostMapping(value = "/user")
    public void createUser(@RequestBody User user){
        try {
            if (user.getEmail() == null) {
                throw new InvalidEmailException("Отсутствует Email");
            } else if (users.containsKey(user.getEmail())){
                throw new UserAlreadyExistException("Пользователь с таким Email уже существует");
            }
            users.put(user.getEmail(), user);
            log.debug("Пользователь {} сохранен", user.getEmail());
        } catch (InvalidEmailException | UserAlreadyExistException exception){
            log.debug("Пользователь несохранен");
            System.out.println(exception.getMessage());
        }
    }

    @PutMapping(value = "/user")
    public void changeUser(@RequestBody User user){
        try {
            if (user.getEmail() == null) {
                throw new InvalidEmailException("Отсутствует Email");
            }
            users.put(user.getEmail(), user);
        } catch (InvalidEmailException exception){
            System.out.println(exception.getMessage());
        }
    }
}
