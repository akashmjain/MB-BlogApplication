package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.enitity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findById(int theId);

    public void save(User theEmployee);

    public void deleteById(int theId);
}
