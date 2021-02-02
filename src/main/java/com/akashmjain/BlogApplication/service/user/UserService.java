package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.enitity.UserEntity;

import java.util.List;

public interface UserService {
    public List<UserEntity> findAll();

    public UserEntity findById(int theId);

    public void save(UserEntity userEntity);

    public void deleteById(int theId);
}
