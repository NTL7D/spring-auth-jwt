package com.ntl7d.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping()
    public String hello() {
        return "Hello from secure endpoint";
    }

}
