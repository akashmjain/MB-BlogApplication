package com.akashmjain.BlogApplication.service.blog;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{
    private PostService postService;
    private UserService userService;
    private TagService tagService;


    @Autowired
    public BlogServiceImpl(PostService postService, UserService userService, TagService tagService) {
        this.postService = postService;
        this.userService = userService;
        this.tagService = tagService;
    }

    @Override
    public Page<PostEntity> getBlogPostsForFrontPage(int pageNo, int limit, Model model) {
        Pageable page = PageRequest.of(pageNo, limit);
        Page<PostEntity> posts = postService.findPages(page);
        return posts;
    }

    @Override
    public PostEntity getIndividualBlogPost(int postId) {
        return postService.findById(postId);
    }

    @Override
    public List<PostEntity> getBlogPostsByAuthor(int authorId) {
        UserEntity user = userService.findById(authorId);
        return user.getPosts();
    }

    @Override
    public List<PostEntity> getBlogPostsByTags(int tagId) {
        TagEntity tag = tagService.findById(tagId);
        return tag.getPosts();
    }

}
