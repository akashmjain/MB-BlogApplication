package com.akashmjain.BlogApplication.service.post;

import com.akashmjain.BlogApplication.dao.PostRepository;
import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.Post;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(int theId) {
        Optional<Post> result = postRepository.findById(theId);
        Post post = null;
        if(result.isPresent()) {
            post = result.get();
        } else {
            // @TODO throw new RuntimeException here
            System.out.println("Hey problem is there");
        }
        return post;
    }

    @Override
    public void save(Post theEmployee) {
        postRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        postRepository.deleteById(theId);
    }
}
