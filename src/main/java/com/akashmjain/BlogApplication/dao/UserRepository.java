package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
