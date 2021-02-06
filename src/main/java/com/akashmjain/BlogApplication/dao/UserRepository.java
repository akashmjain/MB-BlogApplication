package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByNameContaining (String title);
}
