package com.akashmjain.BlogApplication.beans;

import com.akashmjain.BlogApplication.enitity.Post;
import com.akashmjain.BlogApplication.enitity.Tag;

import java.util.List;

public class Blog {
    private Post blogPost;
    private List<Tag> blogTags;

    public Post getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(Post blogPost) {
        this.blogPost = blogPost;
    }

    public List<Tag> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(List<Tag> blogTags) {
        this.blogTags = blogTags;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blog=" + blogPost +
                ", blogTags=" + blogTags +
                '}';
    }
}
