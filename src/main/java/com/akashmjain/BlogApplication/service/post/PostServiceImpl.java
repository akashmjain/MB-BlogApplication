package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.dao.PostRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<PostEntity> findPages(Pageable page) {
        Page<PostEntity> pages = postRepository.findAll(page);
        return pages;
    }

    @Override
    public PostEntity findById(int theId) {
        Optional<PostEntity> result = postRepository.findById(theId);
        PostEntity postEntity = null;
        if(result.isPresent()) {
            postEntity = result.get();
        } else {
            // @TODO throw new RuntimeException here
            System.out.println("Hey problem is there");
        }
        return postEntity;
    }

    @Override
    public void save(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    @Override
    public void deleteById(int theId) {
        postRepository.deleteById(theId);
    }

    @Override
    public List<PostEntity> getPostsBySearchString(String query) {
        List<PostEntity> posts = new ArrayList<>();

        // get from title
        posts.addAll(postRepository.findByTitleContaining(query));
        // get from content
        posts.addAll(postRepository.findByContentContaining(query));
        // get from excerpt
        posts.addAll(postRepository.findByContentContaining(query));

        return posts;
    }
}
