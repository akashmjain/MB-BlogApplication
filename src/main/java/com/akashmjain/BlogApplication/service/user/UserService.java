package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.enitity.Users;

import java.util.List;

public interface UserService {
    public List<Users> findAll();

    public Users findById(int theId);

    public void save(Users users);

    public void deleteById(int theId);
}
