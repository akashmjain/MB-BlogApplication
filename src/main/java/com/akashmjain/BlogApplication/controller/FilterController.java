package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/filter")
    public String getPostByAuthor(@RequestParam("userId") int userId, @RequestParam(name = "page", required = false, defaultValue = "0") int pageNo,Model model) {
        Pageable pageable = PageRequest.of(pageNo, 3);
        UserEntity userEntity = userService.findById(userId);
        Long start = pageable.getOffset();
        Long end =(start + pageable.getPageSize()) > userEntity.getPosts().size() ? userEntity.getPosts().size() : (start + pageable.getPageSize());
        Page<PostEntity> pages = new PageImpl<PostEntity>(userEntity.getPosts().subList(start.intValue(), end.intValue()), pageable, userEntity.getPosts().size());
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("posts", pages.getContent());
        model.addAttribute("pathTo", "/filter?userId="+userId);
        return "index";
    }
}
