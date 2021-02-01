package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.Users;
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
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(int theId) {
        Optional<Users> result = userRepository.findById(theId);
        Users users = null;
        if(result.isPresent()) {
            users = result.get();
        } else {
            throw new RuntimeException("User not found with ID - " + theId);
        }
        return users;
    }

    @Override
    public void save(Users theEmployee) {
        userRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }
}
