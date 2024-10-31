package com.practice.msa.gpt.common.config;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * title : WebClientConfig
 *
 * description : GPT와의 통신을 위한 WebClient의 기본 헤더 및 타임아웃 설정
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.10.31
 **/
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientConfig {
    private final GptConfig gptConfig;

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(gptConfig.getReadTimeOut()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, gptConfig.getConnectionTimeOut());


        final WebClient webClient = WebClient.builder()
                .baseUrl(gptConfig.getApiUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Authorization", "Bearer " + gptConfig.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();

        log.info("=======================================================================================================================");
        log.info("WebClient initialized");
        log.info("api timeout config setting");
        log.info("webClient config setting");
        log.info("=======================================================================================================================");

        return webClient;
    }
}
