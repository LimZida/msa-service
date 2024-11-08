package com.practice.msa.welcome.controller;

import com.practice.msa.common.controller.AbstractBaseController;
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
@RequestMapping("/api/v1/drug")
@AllArgsConstructor
public class WelcomeController extends AbstractBaseController {

    @GetMapping("")
    public String welcome(){

        return "api welcome Page";
    }
}
