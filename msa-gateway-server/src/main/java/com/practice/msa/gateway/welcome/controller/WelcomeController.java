package com.practice.msa.gateway.welcome.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * title : WelcomeController
 *
 * description : 웰컴페이지
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.10.25
 **/
@RestController
@Slf4j
@RequestMapping("/gateway")
@AllArgsConstructor
public class WelcomeController{

    @GetMapping("")
    public String welcome(){
        return "welcome Page";
    }
}
