package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blogs/", method = RequestMethod.GET)
    public ResponseEntity<List<Blog>> listAllBlogs() {
        try {

            List<Blog> blogs = blogService.findAll();
            if (blogs.isEmpty()) {
                return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Blog>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/blogs/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBlogs(@RequestBody Blog blog, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(" Create Blogs: " + blog.getTitle() + blog.getContent());
        blogService.saveBlog(blog);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/blogs/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") long id, @RequestBody Blog blog) {
        System.out.println("Updating Blogs " + id);

        Blog currentBlog = blogService.findById(id);

        if (currentBlog == null) {
            System.out.println("Blogs with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        currentBlog.setTitle(blog.getTitle());
        currentBlog.setContent(blog.getContent());
        currentBlog.setId(blog.getId());

        blogService.saveBlog(currentBlog);
        return new ResponseEntity<Blog>(currentBlog, HttpStatus.OK);
    }


    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog> deleteBlog(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Blog with id " + id);

        Blog blog = blogService.findById(id);
        if (blog == null) {
            System.out.println("Unable to delete. Blogs with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        blogService.remove(id);
        return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
    }


}
