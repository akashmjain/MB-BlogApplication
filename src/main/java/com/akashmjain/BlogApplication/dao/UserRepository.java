package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}
