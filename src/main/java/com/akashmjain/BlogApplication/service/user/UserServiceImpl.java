package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
