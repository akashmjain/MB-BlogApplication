package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public List<UserEntity> findByNameContaining (String title);

    public Optional<UserEntity> findByName (String name);

}

