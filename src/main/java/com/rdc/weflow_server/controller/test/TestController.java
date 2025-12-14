package com.rdc.weflow_server.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ctest")
    public String test() {
        return "cicd test, hello world";
    }

}
