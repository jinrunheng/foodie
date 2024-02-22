package com.github.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author Dooby Kim
 * @Date 2024/2/11 下午9:20
 * @Version 1.0
 */
@RestController
@ApiIgnore
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
