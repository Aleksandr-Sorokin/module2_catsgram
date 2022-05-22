package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private static Integer globalIdPosts = 0;
    private static final String DESC = "desc";

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    private static Integer createNextId(){
        return globalIdPosts++;
    }

    public List<Post> findPostByEmail(String email, Integer size, String sort){
        return posts.stream().filter(post -> email.equals(post.getAuthor()))
                .sorted((o1, o2) -> {
                    int result = o1.getCreationDate().compareTo(o2.getCreationDate());
                    if (sort.equals(DESC)){
                       result =  result * -1;
                    }
                    return result;
                }).limit(size).collect(Collectors.toList());
    }

    public Post findPostById(Integer postId) {
        return posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост с id %s отсутствует", postId)));
    }

    public List<Post> findAll(Integer size, String sort, Integer from) {
        return posts.stream().sorted((date1, date2) -> {
                    int resultCompare = date1.getCreationDate().compareTo(date2.getCreationDate());
                    if (sort.equals(DESC)){
                        resultCompare = resultCompare * -1;
                    }
                    return resultCompare;
                })
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null){
            throw new UserNotFoundException(String.format("Пользователь %s не найден", post.getAuthor()));
        }
        post.setId(createNextId());
        posts.add(post);
        return post;
    }
}