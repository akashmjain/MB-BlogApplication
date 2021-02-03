package com.akashmjain.BlogApplication.service.comment;

import com.akashmjain.BlogApplication.dao.CommentRepository;
import com.akashmjain.BlogApplication.enitity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public CommentEntity findById(int theId) {
        Optional<CommentEntity> optional = commentRepository.findById(theId);
        CommentEntity tagEntity = null;
        if(optional.isPresent()) {
            tagEntity = optional.get();
        } else {
            throw new RuntimeException("Not found any Comment with ID - " + theId);
        }
        return tagEntity;
    }

    @Override
    public void save(CommentEntity postEntity) {
        commentRepository.save(postEntity);
    }

    @Override
    public void deleteById(int theId) {
        commentRepository.deleteById(theId);
    }
}
