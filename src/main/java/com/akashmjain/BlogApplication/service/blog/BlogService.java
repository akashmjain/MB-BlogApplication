package com.akashmjain.BlogApplication.service.blog;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.List;

public interface BlogService {
    public Page<PostEntity> getBlogPostsForFrontPage(int pageNo, int limit, Model model);

    public PostEntity getIndividualBlogPost(int postId);

    public List<PostEntity> getBlogPostsByAuthor(int authorId);

    public List<PostEntity> getBlogPostsByTags(int tagId);

    // public List<PostEntity> getBlogPostsByPublishedDateTime(Timestamp timestamp);
}
