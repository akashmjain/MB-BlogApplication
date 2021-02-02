package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
