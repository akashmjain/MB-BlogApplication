package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    public List<PostEntity> findAll();

    public Page<PostEntity> findPages(Pageable page);

    public PostEntity findById(int theId);

    public void save(PostEntity postEntity);

    public void deleteById(int theId);

    public List<PostEntity> getPostsBySearchString(String query);
}
