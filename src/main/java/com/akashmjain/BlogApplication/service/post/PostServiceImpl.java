package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.dao.PostRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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
    public void save(PostEntity theEmployee) {
        postRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        postRepository.deleteById(theId);
    }
}
