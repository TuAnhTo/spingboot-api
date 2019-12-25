package com.codegym.controller;

import com.codegym.model.Blog;
//import com.codegym.model.Customer;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blogs/", method = RequestMethod.GET )
    public ResponseEntity<List<Blog>> listAllBlogs(){
        try {

            List<Blog> blogs = blogService.findAll();
            if (blogs.isEmpty()){
                return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Blog>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/blogs/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBlogs(@RequestBody Blog blog, UriComponentsBuilder uriComponentsBuilder){
        System.out.println(" Create Blogs: " +blog.getTitle() + blog.getContent());
        blogService.saveBlog(blog);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/blogs/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders,HttpStatus.CREATED);
    }
}
