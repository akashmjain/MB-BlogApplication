package com.akashmjain.BlogApplication.service.comment;

import com.akashmjain.BlogApplication.enitity.CommentEntity;

public interface CommentService {
    public CommentEntity findById(int theId);

    public void save(CommentEntity commentEntity);

    public void deleteById(int theId);
}
