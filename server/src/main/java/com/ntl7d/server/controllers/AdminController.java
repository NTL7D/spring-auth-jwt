package com.ntl7d.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("admin")
public class AdminController {
    @GetMapping()
    public String hello() {
        return "welcome to the admin zone";
    }

}
