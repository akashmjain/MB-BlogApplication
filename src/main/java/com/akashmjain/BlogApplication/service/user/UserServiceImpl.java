package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(int theId) {
        Optional<UserEntity> result = userRepository.findById(theId);
        UserEntity userEntity = null;
        if(result.isPresent()) {
            userEntity = result.get();
        } else {
            throw new RuntimeException("User not found with ID - " + theId);
        }
        return userEntity;
    }

    @Override
    public void save(UserEntity theEmployee) {
        userRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public List<PostEntity> getPostsByUserIdList(List<Integer> authorIds, List<PostEntity> postEntities) {
        if (postEntities == null || postEntities.isEmpty()) {
            for (int id : authorIds) {
                postEntities.addAll(this.findById(id).getPosts());
            }
        } else {
            postEntities.removeIf(post -> !authorIds.contains(post.getAuthor().getId()));
        }
        return postEntities;
    }

    @Override
    public List<PostEntity> getPostsByUserName(String query) {
        List<UserEntity> userEntities = userRepository.findByNameContaining(query);
        List<PostEntity> postEntities = new ArrayList<>();
        for (UserEntity user : userEntities) {
            postEntities.addAll(user.getPosts());
        }
        return postEntities;
    }

    @Override
    public UserEntity findByName(String name) {
        return userRepository.findByName(name).get();
    }
}
