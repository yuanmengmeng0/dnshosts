package com.enorth.dns.dnshosts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "com.enorth.dns.dnshosts.dao")
@EnableCaching
@ServletComponentScan
public class DnshostsApplication {

    public static void main(String[] args) {

        SpringApplication.run(DnshostsApplication.class, args);
    }

}
