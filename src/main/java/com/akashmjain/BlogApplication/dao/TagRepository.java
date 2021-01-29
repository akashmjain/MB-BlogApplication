package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
