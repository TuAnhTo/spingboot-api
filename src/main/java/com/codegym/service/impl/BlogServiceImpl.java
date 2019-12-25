package com.codegym.service.impl;

import com.codegym.model.Blog;
import com.codegym.repository.BlogRepository;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;


    @Override
    public List<Blog> findAll() {
        Iterable<Blog> iterable = blogRepository.findAll();
        List<Blog> listBlogs = new ArrayList<>();
        for (Blog blog : iterable) {
            listBlogs.add(blog);
        }
        return listBlogs;
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public void saveBlog(Blog model) {
        blogRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        blogRepository.deleteById(id);
    }
}
