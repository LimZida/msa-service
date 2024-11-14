package com.practice.msa.common.util;

import com.practice.msa.common.config.ApiConfig;
import com.practice.msa.drug.dto.ApiReqDTO;
import com.practice.msa.drug.dto.ExportResDTO;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
/**
 * title : ApiUtilTest
 *
 * description : 통신 상황 가정 mock test
 *
 *               동작 원리 -
 *                          0. request, response 생성 및 의존관계 설정
 *                          1. mockWebServer 생성 및 url 설정
 *                          2. ApiUtil에 testWebClient 주입 (Test GPT Server config)
 *                          3. mockWebServer.enqueue를 통해 정상 or 에러 응답 설정
 *                          4. 가짜 서버에 API 통신을 통해 비즈니스 로직 확인
 *                          5. mockWebServer 종료
 *
 *
 * reference : https://055055.tistory.com/118 , https://jie0025.tistory.com/545 , https://www.devkuma.com/docs/mock-web-server/
 *
 *             비동기 통신 테스트 시 실수 : https://yang1s.tistory.com/12
 *
 * author : 임현영
 *
 * date : 2024.11.07
 **/
@SpringBootTest
@Slf4j
class ApiUtilTest {

    @Autowired
    private ApiUtil apiUtil;
    @Autowired
    private ApiConfig apiConfig;

    private Map request = new ConcurrentHashMap<>();
    public ApiReqDTO apiReqDTO = new ApiReqDTO();
    private static MockWebServer mockWebServer;
    private String mockResponseJson = """
            {
                "header": {
                    "resultCode": "00",
                    "resultMsg": "NORMAL SERVICE."
                },
                "body": {
                    "pageNo": 1,
                    "totalCount": 15,
                    "numOfRows": 10,
                    "items": [
                        {
                            "TRMT_YM": "201912",
                            "NARK_DIVS_NM": "마약",
                            "EXPRT_ENTP_NUM": "1",
                            "EXPRT_NATN_NUM": "1",
                            "PRDLST_NUM": "1",
                            "EXPRT_QTY": "3600",
                            "EXPRT_AMT": "5240"
                        }
                    ]
                }
            }
        """;

    @BeforeAll
    static void startServer() throws IOException {
        /*
        * mock server start
        * */
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialize() {
        apiReqDTO.setType("json");
        apiReqDTO.setPageNo(1);
        apiReqDTO.setNumOfRows(10);
        apiReqDTO.setTrmtYr("2024");

        /*
        * mock server setting
        * */

        // 현재 WebClientConfig 에 url, api key 등등 전부 선언되어있기 때문에 굳이 재설정 해줄 필요가 없다.
        // ApiUtil 자체도 Custom WebClient랑 연동이 되어있기 때문에 기본생성자로 다시 만들 이유가 없다.
        // 하지만 이렇게 되면 실제 ChatGpt server와 통신하므로, 가짜 서버 객체를 만들려면 재정의 해야한다.

        // MockWebServer URL 설정
        final String baseUrl = mockWebServer.url("/test/mock/drug").toString();

        // testWebClient 설정 (WebClinetConfig.java와 동일하나 baseUrl을 mockWebServer 설정으로 변경)
        final HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(apiConfig.getReadTimeOut()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, apiConfig.getConnectionTimeOut());

        final WebClient testWebClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Authorization", "Bearer " + apiConfig.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();

        apiUtil = new ApiUtil(testWebClient, apiConfig);
    }

    @AfterAll
    static void destroy() throws IOException {
        mockWebServer.shutdown();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendGetMessageAndResAllByAsync() {

        /*
        * 200 응답 설정 - given
        * */
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseJson)
                .addHeader("Content-Type", "application/json"));

        /*
        * GPT API 요청 - when
        * */
        final Mono<ExportResDTO> resultMono = apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, "");

        // 응답값 검증 - then
        StepVerifier.create(resultMono)
            .expectNextMatches(data -> {
                // 원하는 검증 로직 추가
                return data.getPageNo() == 1 &&
                        data.getTotalCount() == 15 &&
                        data.getItems().get(0).getTrmtYm().equals("201912");
            })
            .verifyComplete();

    }

    @Test
    void sendGetMessageAndResAllByAsync_error() {
        /*
        * 401 응답 설정 - given
        * */
//        mockWebServer.enqueue(new MockResponse().setResponseCode(401));
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        /*
        * GPT API 요청 - when
        * */
        final Mono<ExportResDTO> resultMono = apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, "");

        // 응답값 검증 - then
        StepVerifier.create(resultMono)
            .expectNextMatches(data -> {
                // 원하는 검증 로직 추가
                return data.getPageNo() == 1 &&
                        data.getTotalCount() == 15 &&
                        data.getItems().get(0).getTrmtYm().equals("201912");
            })
            .verifyComplete();

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