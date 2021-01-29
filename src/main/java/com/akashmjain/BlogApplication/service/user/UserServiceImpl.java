package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int theId) {
        Optional<User> result = userRepository.findById(theId);
        User user = null;
        if(result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("User not found with ID - " + theId);
        }
        return user;
    }

    @Override
    public void save(User theEmployee) {
        userRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }
}
