package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author Dooby Kim
 * @Date 2024/2/11 下午9:18
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.github.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
