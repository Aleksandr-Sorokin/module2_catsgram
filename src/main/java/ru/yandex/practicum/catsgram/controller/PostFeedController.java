package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> getFeedFriends(@RequestBody String dataParameter) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendsPost friendsPost;
        List<Post> result = new ArrayList<>();
        try {
            String refactorDataParameter = objectMapper.readValue(dataParameter, String.class);
            friendsPost = objectMapper.readValue(refactorDataParameter, FriendsPost.class);
        } catch (JsonProcessingException exception){
            throw new RuntimeException("Проблема с форматом json", exception);
        }
        if (friendsPost != null){
            for (String friend : friendsPost.friends){
                result.addAll(postService.findPostByEmail(friend, friendsPost.getSize(), friendsPost.getSort()));
            }
            return result;
        } else {
            throw new RuntimeException("Проблема с параметрами в стороке запроса");
        }
    }
    static class FriendsPost{
        private String sort;
        private Integer size;
        private List<String> friends;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public List<String> getFriends() {
            return friends;
        }

        public void setFriends(List<String> friends) {
            this.friends = friends;
        }
    }
}
