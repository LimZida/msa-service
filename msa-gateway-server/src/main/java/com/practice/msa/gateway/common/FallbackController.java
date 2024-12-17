package com.practice.msa.gateway.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * title : FallbackController
 *
 * description : api 통신 오류 Controller
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.10.25
 **/
@RestController
@Slf4j
@RequestMapping("/fallback")
@AllArgsConstructor
public class FallbackController {

    @GetMapping("/chat-service")
    public String chatFail(){
        return "fail chat service";
    }

    @GetMapping("/api-service")
    public String apiFail(){
        return "fail api service";
    }
}
