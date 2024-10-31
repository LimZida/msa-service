package com.practice.msa.gpt.common.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * title : GptConfig
 *
 * description : gpt properties 설정
 *
 * reference : properties 한글 깨지는 현상 https://akageun.github.io/2018/07/04/spring-propertysource.html
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@Configuration
@Getter
@Slf4j
public class GptConfig {
    private final String model;
    private final String apiKey;
    private final String apiUrl;
    private final int readTimeOut;
    private final int connectionTimeOut;

    public GptConfig(
            @Value("${openai.model}") String model,
            @Value("${openai.api.key}") String apiKey,
            @Value("${openai.api.url}") String apiUrl,
            @Value("${openai.api.read.timeout}") int readTimeOut,
            @Value("${openai.api.connection.timeout}") int connectionTimeOut
    ) {
        this.model = model;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.readTimeOut = readTimeOut;
        this.connectionTimeOut = connectionTimeOut;

        log.info("=======================================================================================================================");
        log.info("GptConfig initialized");
        log.info("model={}, apiKey={}", model, apiKey);
        log.info("apiUrl={}", apiUrl);
        log.info("readTimeOut={}, connectionTimeOut = {}",readTimeOut, connectionTimeOut);
        log.info("=======================================================================================================================");
    }
}
