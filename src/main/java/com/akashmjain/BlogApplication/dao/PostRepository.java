package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
