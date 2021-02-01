package com.akashmjain.BlogApplication.service;

import com.akashmjain.BlogApplication.beans.Blog;
import com.akashmjain.BlogApplication.enitity.Post;
import com.akashmjain.BlogApplication.enitity.Tag;
import com.akashmjain.BlogApplication.enitity.User;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private PostService postService;
    private TagService tagService;
    private UserService userService;

    @Autowired
    public BlogServiceImpl(PostService postService, TagService tagService, UserService userService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public List<Post> getBlogsForFrontPage() {
        return postService.findAll();
    }

    @Override
    public Post getIndividualBlog(int id) {
        return postService.findById(id);
    }

    @Override
    public List<Blog> getBlogPostByAuthor(User user) {
        return null;
    }

    @Override
    public List<Blog> getBlogPostByPublishedDateTime(Timestamp timestamp) {
        return null;
    }

    @Override
    public List<Blog> getBlogPostByTags(List<Tag> tags) {
        return null;
    }
}
