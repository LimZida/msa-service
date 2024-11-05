package com.practice.msa.welcome.controller;

import com.practice.msa.common.controller.AbstractBaseController;
import com.practice.msa.welcome.service.WelcomeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
@AllArgsConstructor
public class WelcomeController extends AbstractBaseController {
    private final WelcomeService welcomeService;

    @GetMapping("")
    public String welcome(){
        welcomeService.welcome();

        return "welcome Page";
    }
}
