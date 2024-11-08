package com.practice.msa.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
/**
 * title : ApiUtilTest
 *
 * description : 통신 상황 가정 mock test
 *
 *               동작 원리 -
 *                          0. request, response 생성 및 의존관계 설정
 *                          1. mockWebServer 생성 및 url 설정
 *                          2. GptApiUtil에 testWebClient 주입 (Test GPT Server config)
 *                          3. mockWebServer.enqueue를 통해 정상 or 에러 응답 설정
 *                          4. 가짜 서버에 API 통신을 통해 비즈니스 로직 확인
 *                          5. mockWebServer 종료
 *
 *
 * reference : https://055055.tistory.com/118 , https://jie0025.tistory.com/545 , https://www.devkuma.com/docs/mock-web-server/
 *
 * author : 임현영
 *
 * date : 2024.11.07
 **/
@SpringBootTest
@Slf4j
class ApiUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendGetMessageAndResAllByAsync() {
    }

    @Test
    void sendPostMessageAndResAllByAsync() {
    }

    @Test
    void sendPostMessageAndResBody() {
    }

    @Test
    void sendPostMessageAndResAll() {
    }
}