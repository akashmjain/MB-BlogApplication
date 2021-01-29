package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.enitity.Tag;
import com.akashmjain.BlogApplication.enitity.User;

import java.util.List;

public interface TagService {
    public List<Tag> findAll();

    public Tag findById(int theId);

    public void save(Tag tag);

    public void deleteById(int theId);
}