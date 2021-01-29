package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.enitity.Post;

import java.util.List;

public interface PostService {
    public List<Post> findAll();

    public Post findById(int theId);

    public void save(Post post);

    public void deleteById(int theId);
}
