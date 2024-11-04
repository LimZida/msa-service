package com.practice.msa.gpt.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.msa.gpt.common.config.GptConfig;
import com.practice.msa.gpt.common.exception.custom.CustomRequestException;
import com.practice.msa.gpt.qna.dto.GptReqDTO;
import com.practice.msa.gpt.qna.dto.GptResDTO;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * title : GptApiUtilTest
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
 * reference : https://055055.tistory.com/118 , https://jie0025.tistory.com/545
 *
 * author : 임현영
 *
 * date : 2024.11.04
 **/
@SpringBootTest
@Slf4j
class GptApiUtilTest {

    @Autowired
    private GptApiUtil gptApiUtil;
    @Autowired
    private GptConfig gptConfig;

    private MockWebServer mockWebServer;
    private GptResDTO response = new GptResDTO();
    private GptReqDTO request = new GptReqDTO();

    @BeforeEach
    void setUp() throws IOException {
        /*
        * Request Setting
        * */
        List<String> messages = new ArrayList<>();
        messages.add("피자에 대해 짧게 설명해주세요.");
        request = new GptReqDTO("gpt-3.5-turbo",messages);

        /*
        * Response Setting
        * */
        response.setCreated("1730691851");
        response.setObject("chat.completion");
        response.setModel("gpt-3.5-turbo-0125");

        GptResDTO.Message message = new GptResDTO.Message();
        message.setRole("assistant");
        message.setContent("피자는 둥근 모양의 밀가루 반죽에 토마토 소스와 치즈를 발라 구워 만든 이탈리아 음식으로, 다양한 토핑을 올려 먹는 인기 있는 음식입니다. 혼자 먹거나 친구, 가족과 함께 즐기는 식사로 많이 선호되며, 다양한 맛과 종류로 제공되어 다양한 취향에 맞게 즐길 수 있습니다.");

        GptResDTO.Choice choice = new GptResDTO.Choice();
        choice.setIndex(0);
        choice.setMessage(message);
        response.setChoices(Collections.singletonList(choice));

        /*
        * mock server setting
        * */
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // 현재 WebClientConfig 에 url, api key 등등 전부 선언되어있기 때문에 굳이 재설정 해줄 필요가 없다.
        // gptApiUtil 자체도 Custom WebClient랑 연동이 되어있기 때문에 기본생성자로 다시 만들 이유가 없다.
        // 하지만 이렇게 되면 실제 ChatGpt server와 통신하므로, 가짜 서버 객체를 만들려면 재정의 해야한다.

        // MockWebServer URL 설정
        String baseUrl = mockWebServer.url("/test/mock/gpt").toString();

        // testWebClient 설정 (WebClinetConfig.java와 동일하나 baseUrl을 mockWebServer 설정으로 변경)
        final HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(gptConfig.getReadTimeOut()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, gptConfig.getConnectionTimeOut());

        final WebClient testWebClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Authorization", "Bearer " + gptConfig.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();

        gptApiUtil = new GptApiUtil(testWebClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testSendMessageAndResAll_Success() throws JsonProcessingException, InterruptedException {

        /*
        * 200 응답 설정
        * */
        String jsonResponse = new ObjectMapper().writeValueAsString(response);
        mockWebServer.enqueue(new MockResponse().setBody(jsonResponse).setResponseCode(200));

        /*
        * GPT API 요청
        * */
        GptResDTO actualResponse = gptApiUtil.sendMessageAndResAll(request);
        log.info("actual: {}",actualResponse.getChoices().get(0).getMessage().getContent());
        log.info("mock: {}",response.getChoices().get(0).getMessage().getContent());

        // 요청 URL 검증
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        log.info("mock url: {}",recordedRequest.getPath());

        // 응답값 검증
        assertThat(actualResponse.getChoices().get(0).getMessage().getContent())
                .isEqualTo(response.getChoices().get(0).getMessage().getContent());

//        assertThat(recordedRequest.getPath()).isEqualTo("/test/mock/gpt");
    }

    @Test
    void testSendMessageAndResAll_WebClientException() {
        /*
        * 에러 응답 설정
        * */
        // client error
        mockWebServer.enqueue(new MockResponse().setResponseCode(401));
        // server error
//        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        /*
        * 에러 예상
        * */
        assertThrows(CustomRequestException.class, () -> {
            gptApiUtil.sendMessageAndResAll(request);
        });
    }
}