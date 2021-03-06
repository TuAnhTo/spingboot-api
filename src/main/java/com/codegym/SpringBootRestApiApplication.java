package com.codegym;

import com.codegym.service.BlogService;
import com.codegym.service.CustomerService;
import com.codegym.service.impl.BlogServiceImpl;
import com.codegym.service.impl.CustomerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootRestApiApplication {
    @Bean
    public CustomerService customerService() {
        return new CustomerServiceImpl();
    }

    @Bean
    public BlogService blogService(){ return new BlogServiceImpl();
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApiApplication.class, args);
    }

}
