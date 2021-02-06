package com.akashmjain.BlogApplication.service.user;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public List<UserEntity> findAll();

    public UserEntity findById(int theId);

    public void save(UserEntity userEntity);

    public void deleteById(int theId);

}
