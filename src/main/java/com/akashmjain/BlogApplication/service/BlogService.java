package com.akashmjain.BlogApplication.service;

import com.akashmjain.BlogApplication.beans.Blog;
import com.akashmjain.BlogApplication.enitity.Post;
import com.akashmjain.BlogApplication.enitity.Tag;
import com.akashmjain.BlogApplication.enitity.User;

import java.sql.Timestamp;
import java.util.List;

public interface BlogService {
    public List<Post> getBlogsForFrontPage();

    public Post getIndividualBlog(int id);

    public List<Blog> getBlogPostByAuthor(User user);
    
    public List<Blog> getBlogPostByPublishedDateTime(Timestamp timestamp);

    public List<Blog> getBlogPostByTags(List<Tag> tags);
}
