package com.tiansu.energy.ltc.module.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class XssController {

    @GetMapping("/xss")
    public String xss(String params){
        return params;
    }



}
