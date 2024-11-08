package com.practice.msa.common.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * title : ApiConfig
 *
 * description : 공공 API properties 설정
 *
 * reference : properties 한글 깨지는 현상 https://akageun.github.io/2018/07/04/spring-propertysource.html
 *
 * author : 임현영
 * date : 2024.11.05
 **/
@Configuration
@Getter
@Slf4j
public class ApiConfig {
    private final String apiKey;
    private final String apiKeyDecoding;
    private final String apiUrl;
    private final int readTimeOut;
    private final int connectionTimeOut;

    public ApiConfig(
            @Value("${data.api.key.encoding}") String apiKey,
            @Value("${data.api.key.decoding}") String apiKeyDecoding,
            @Value("${data.api.url}") String apiUrl,
            @Value("${data.api.read.timeout}") int readTimeOut,
            @Value("${data.api.connection.timeout}") int connectionTimeOut
    ) {
        this.apiKey = apiKey;
        this.apiKeyDecoding = apiKeyDecoding;
        this.apiUrl = apiUrl;
        this.readTimeOut = readTimeOut;
        this.connectionTimeOut = connectionTimeOut;

        log.info("=======================================================================================================================");
        log.info("ApiConfig initialized");
        log.info("apiKey={}", apiKey);
        log.info("apiKeyDecoding={}", apiKeyDecoding);
        log.info("apiUrl={}", apiUrl);
        log.info("readTimeOut={}, connectionTimeOut = {}",readTimeOut, connectionTimeOut);
        log.info("=======================================================================================================================");
    }
}
