package com.akashmjain.BlogApplication.service.blog;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;

import java.sql.Timestamp;
import java.util.List;

public interface BlogService {
    public List<PostEntity> getBlogPostsForFrontPage();

    public PostEntity getIndividualBlogPost(int postId);

    public List<PostEntity> getBlogPostsByAuthor(int authorId);

    public List<PostEntity> getBlogPostsByTags(int tagId);

    // public List<PostEntity> getBlogPostsByPublishedDateTime(Timestamp timestamp);
}
