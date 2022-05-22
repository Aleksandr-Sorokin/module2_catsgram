package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    private final PostService postService;
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/post/{postId}")
    public Post findPostById(@PathVariable int postId) {
        return postService.findPostById(postId);
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        if (!(sort.equals("asc") || sort.equals("desc"))){
            throw new IllegalArgumentException();
        }
        if (page < 0 || size < 1){
            throw new IllegalArgumentException();
        }
        Integer from = page * size;
        return postService.findAll(size, sort, from);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}