package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.enitity.PostEntity;

import java.util.List;

public interface PostService {
    public List<PostEntity> findAll();

    public PostEntity findById(int theId);

    public void save(PostEntity postEntity);

    public void deleteById(int theId);
}
