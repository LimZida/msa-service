package com.practice.msa.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practice.msa.common.config.ApiConfig;
import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.drug.dto.ApiReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

import static com.practice.msa.common.enums.ErrorEnum.*;

/**
 * title : ApiUtil
 *
 * description :  API과의 통신 지원, 비동기, 동기통신 GET POST 메소드 존재
 *
 *
 * reference :  구현 예제 : https://jypark1111.tistory.com/203
 *             에러처리 : https://dkswnkk.tistory.com/708 , https://icthuman.tistory.com/entry/Spring-WebClient-%EC%82%AC%EC%9A%A9-3-Configuration-Timeout
 *             retry : https://velog.io/@kindtiger95/Spring-WebClient-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
 *             비동기 통신 :  https://annajin.tistory.com/228
 *                          https://velog.io/@gda05189/Webclient%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%99%B8%EB%B6%80-API-%EB%B9%84%EB%8F%99%EA%B8%B0-%ED%86%B5%EC%8B%A0-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
 *                          https://suyeonchoi.tistory.com/61
 *             Webclient 통신 가이드 : https://yangbongsoo.tistory.com/29
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiUtil {
    private final WebClient webClient;
    private final ApiConfig apiConfig;

    /*
    * header + body 응답 받을 경우 - 비동기 GET
    * */
    public <T extends ApiReqDTO, R> Mono<R> sendGetMessageAndResAllByAsync(T apiReqDto, Class<R> apiResDtoClass, String prefix) {
        log.info("############ Async ResAll API GET REQUEST with Query Params ############");
        apiReqDto.setServiceKey(apiConfig.getApiKeyDecoding());


        return webClient.get()
                .uri(uriBuilder -> {
                    // prefix 설정
                    // ex) https://example.com/${prefix}
                    uriBuilder.path(prefix);
                    // apiReqDto의 필드를 쿼리 파라미터로 추가
                    Map<String, String> queryParams = ConvertMapper.convertDtoToMap(apiReqDto);
                    queryParams.forEach(uriBuilder::queryParam);

                    LogUtil.requestLogging(queryParams);
                    log.info("Full GET Request URL: {}", uriBuilder.build());
                    return uriBuilder.build();
                })
                .exchangeToMono(response -> {
                    log.info("############ Async API RESPONSE ############");
                    log.info("##### Async API STATUS CODE : {}", response.statusCode());

                    // 상태 코드가 200일 경우
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(ObjectNode.class)
                                .doOnNext(res -> log.info("##### Async VALUE : {}", res))
                                .flatMap(res -> {
                                    // "body" 필드 추출
                                    JsonNode bodyNode = res.path("body");
                                    // String의 응답값을 DTO로 변환
                                    R apiResDTO = ConvertMapper.convertStringToDTO(bodyNode.toString(), apiResDtoClass);
                                    LogUtil.responseLogging(apiResDTO);
                                    log.info("############ Async ResAll to Body CONVERTED RES RETURN ############");
                                    return Mono.just(apiResDTO);
                                });
                    } else {
                        log.error("############ Async ERROR ############");
                        return response.createException().flatMap(Mono::error);
                    }
                })
                .doOnError(e -> log.error("Async ResAll Error occurred before retry: {}", e.toString()))
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(2))
                                .jitter(0.5)
                                .doBeforeRetry(retrySignal -> {
                                    long attempt = retrySignal.totalRetries() + 1;
                                    log.warn("Async ResAll Retrying... Attempt count: {}", attempt);
                                })
                                .filter(throwable -> throwable instanceof WebClientException)
                )
                .onErrorResume(Exception.class, e -> {
                    log.error("Async ResAll API Unexpected Error ::::: {}", e.getMessage());
                    return Mono.error(new CustomRequestException(API99.name(), API99.getMessage(), e));
                });
    }

    /*
    * header + body 응답 받을 경우 - 비동기 GET
    * */
    public <T extends ApiReqDTO, R> Mono<R> sendGetMessageAndResAllByAsync2(T apiReqDto, Class<R> apiResDtoClass, String prefix) {
        log.info("############ Async ResAll API GET REQUEST with Query Params ############");
//        apiReqDto.setServiceKey(apiConfig.getApiKey());
        apiReqDto.setServiceKey(apiConfig.getApiKeyDecoding());


        return webClient.get()
                .uri(uriBuilder -> {
                    // prefix 설정
                    // ex) https://example.com/${prefix}
                    uriBuilder.path(prefix);
                    // apiReqDto의 필드를 쿼리 파라미터로 추가
                    Map<String, String> queryParams = ConvertMapper.convertDtoToMap(apiReqDto);
                    queryParams.forEach(uriBuilder::queryParam);

                    LogUtil.requestLogging(queryParams);
                    log.info("Full GET Request URL: {}", uriBuilder.build());
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(ObjectNode.class)
                                .doOnNext(res -> log.info("##### Async BODY VALUE : {}", res))
                                .flatMap(res -> {
                                    // "body" 필드 추출
                                    JsonNode bodyNode = res.path("body");
                                    // String의 응답값을 DTO로 변환
                                    R apiResDTO = ConvertMapper.convertStringToDTO(bodyNode.toString(), apiResDtoClass);
                                    // String의 응답값을 DTO로 변환
                                    log.info("############ Async ResAll to Body CONVERTED RES RETURN ############");
                                    return Mono.just(apiResDTO);
                                });
    }

    /*
    * header + body 응답 받을 경우 - 비동기 POST
    * */
    public <T extends ApiReqDTO, R> Mono<R> sendPostMessageAndResAllByAsync(T apiReqDto, Class<R> apiResDtoClass, String prefix) {
        log.info("############ Async ResAll  API REQUEST ############");
        LogUtil.requestLogging(apiReqDto);

        return webClient.post()
                // prefix 설정
                // ex) https://example.com/${prefix}
                .uri(uriBuilder -> uriBuilder
                    .path(prefix)
                    .build())
                .bodyValue(apiReqDto)
                // Mono 방식으로 변환
                .exchangeToMono(response -> {
                    log.info("############ Async API RESPONSE ############");
                    log.info("##### Async API STATUS CODE : {}", response.statusCode());
                    // 상태 코드가 200일 경우
                    if (response.statusCode().is2xxSuccessful()) {
                        // 응답 본문을 String으로 변환
                        return response.bodyToMono(ObjectNode.class)
                                .doOnNext(res -> log.info("##### Async BODY VALUE : {}", res))
                                .flatMap(res -> {
                                    // "body" 필드 추출
                                    JsonNode bodyNode = res.path("body");
                                    // String의 응답값을 DTO로 변환
                                    R apiResDTO = ConvertMapper.convertStringToDTO(bodyNode.toString(), apiResDtoClass);
                                    // String의 응답값을 Dto로 반환
                                    log.info("############ Async ResAll to Body CONVERTED RES RETURN ############");
                                    return Mono.just(apiResDTO);
                                });
                    }
                    // 상태 코드가 200이 아닐 경우 예외 처리
                    else {
                        log.error("############ Async ERROR ############");
                        // WebClientResponseException 타입으로 예외를 만들어 반환하는 Mono를 생성
                        // 반드시 flatMap 혹은 throw exception 을 해줘야만이 onErrorResume 에서 catch 하여 오류 핸들링이 가능하다.
                        return response.createException().flatMap(Mono::error);
                    }
                })
                // 재시도 전 오류 로깅
                .doOnError(e -> log.error("Async ResAll Error occurred before retry: {}", e.toString()))
                .retryWhen(
                        // 랜덤 지연시간값 추가 (서로 다른 스레드의 동일 재요청 막기위함)
                        Retry.fixedDelay(3, Duration.ofSeconds(2))
                                .jitter(0.5)
                                .doBeforeRetry(retrySignal -> {
                                    // 현재 재시도 횟수를 로깅
                                    long attempt = retrySignal.totalRetries() + 1;
                                    log.warn("Async ResAll Retrying... Attempt count: {}", attempt);
                                })
                                // 요청 및 응답 통신 에러일 경우에만 재시도
                                // 그 외 예기치 못한 에러일 경우에는 서비스에 영향이 갈 수 있으니 제외
                                .filter(throwable -> throwable instanceof WebClientException)
                )
                // 예외처리
                // Mono.error로 응답해야 Retry 가능
                .onErrorResume(Exception.class, e -> {
                    log.error("Async ResAll API Unexpected Error ::::: {}", e.getMessage());
                    return Mono.error(new CustomRequestException(API99.name(), API99.getMessage(), e));
                });
    }

    /*
    *  body 응답만 받을 경우 - 동기 POST
    * */
    public  <T, R> R sendPostMessageAndResBody(T apiReqDto, Class<R> apiResDtoClass){
        log.info("############  API REQUEST ############");
        LogUtil.requestLogging(apiReqDto);

        String json = webClient.post()
                .bodyValue(apiReqDto)
                .retrieve()
                .bodyToMono(String.class)
                // 재시도 전 오류 로깅
                .doOnError(e -> log.error("Error occurred before retry: {}", e.toString()))
                /*
                * retry 방법 2
                * */
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(2))
                                // 랜덤 지연시간값 추가 (서로 다른 스레드의 동일 재요청 막기위함)
                                .jitter(0.5)
                                .doBeforeRetry(retrySignal -> {
                                    // 현재 재시도 횟수를 로깅
                                    long attempt = retrySignal.totalRetries() + 1; // 0부터 시작하므로 +1
                                    log.warn("Retrying... Attempt count: {}", attempt);
                                })
                .filter(throwable ->
                        // 요청 및 응답 통신 에러일 경우에만 재시도
                        // 그 외 예기치 못한 에러일 경우에는 서비스에 영향이 갈 수 있으니 제외
                        throwable instanceof WebClientException))
                // 예외처리
                .onErrorResume(Exception.class, e -> {
                    // 그 외 예외
                    log.error("API Unexpected Error ::::: {}", e.getMessage());
                    return Mono.error(new CustomRequestException(API99.name(), API99.getMessage(), e));
                })
                // 정상 응답
                .doOnNext(res ->{
                    log.info("############  API RETURN ############");
                    log.info(res);
                })
                // 동기적 (블로킹) 응답 대기
                .block();

        // String의 응답값을 Dto로 반환
        R apiResDTO = ConvertMapper.convertStringToDTO(json, apiResDtoClass);
        log.info("############ CONVERTED RES RETURN ############");
        LogUtil.responseLogging(apiResDTO);
        return apiResDTO;
    }

    /*
    * header + body 응답 받을 경우 - 동기 POST
    * */
    public <T, R> R sendPostMessageAndResAll(T apiReqDto, Class<R> apiResDtoClass) {
        log.info("############ ResAll  API REQUEST ############");
        LogUtil.requestLogging(apiReqDto);

        String json = webClient.post()
                .bodyValue(apiReqDto)
                // Mono 방식으로 변환
                .exchangeToMono(response -> {
                    log.info("############  API RESPONSE ############");
                    log.info("##### API STATUS CODE : {}",response.statusCode());

                    // 상태 코드가 200일 경우
                    if (response.statusCode().is2xxSuccessful()) {
                        // 응답 본문을 String으로 변환
                        return response.bodyToMono(String.class)
                                // 정상 응답 처리
                                .doOnNext(res -> log.info("##### BODY VALUE : {}", res));
                    }
                    // 상태 코드가 200이 아닐 경우 예외 처리
                    else {
                        log.error("############ ERROR ############");
                        // WebClientResponseException 타입으로 예외를 만들어 반환하는 Mono를 생성
                        // 반드시 flatMap 혹은 throw exception 을 해줘야만이 onErrorResume 에서 catch 하여 오류 핸들링이 가능하다.
                        return response.createException().flatMap(Mono::error);
                    }
                })
                // 재시도 전 오류 로깅
                .doOnError(e -> log.error("ResAll Error occurred before retry: {}", e.toString()))
                /*
                 * retry 방법 2
                 * */
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(2))
                                // 랜덤 지연시간값 추가 (서로 다른 스레드의 동일 재요청 막기위함)
                                .jitter(0.5)
                                .doBeforeRetry(retrySignal -> {
                                    // 현재 재시도 횟수를 로깅
                                    long attempt = retrySignal.totalRetries() + 1; // 0부터 시작하므로 +1
                                    log.warn("ResAll Retrying... Attempt count: {}", attempt);
                                })
                                .filter(throwable ->
                                        // 요청 및 응답 통신 에러일 경우에만 재시도
                                        // 그 외 예기치 못한 에러일 경우에는 서비스에 영향이 갈 수 있으니 제외
                                        throwable instanceof WebClientException
                                )
                )
                // 예외처리
                // Mono.error로 응답해야 Retry 가능
                .onErrorResume(Exception.class, e -> {
//                    // WebClientRequestException 내 세부 예외 확인
//                    if (e instanceof WebClientRequestException) {
//                        // 요청 연결 예외
//                        if (e.getCause() instanceof ConnectTimeoutException) {
//                            log.error("ResAll API Connect Timeout Error ::::: {}", e.toString());
//                            return Mono.error(new CustomRequestException(API02.name(), API02.getMessage(), e));
//                        }
//                        // 응답 시간 예외
//                        else if (e.getCause() instanceof ReadTimeoutException) {
//                            log.error("ResAll API Read Timeout Error ::::: {}", e.toString());
//                            return Mono.error(new CustomRequestException(API03.name(), API03.getMessage(), e));
//                        } else {
//                            log.error("ResAll API Request Unexpected Error ::::: {}", e.toString());
//                            return Mono.error(new CustomRequestException(API98.name(), API98.getMessage(), e));
//                        }
//                    }
//                    // 응답 예외
//                    else if (e instanceof WebClientResponseException) {
//                        log.error("ResAll API Response Error ::::: {}", e.toString());
//                        log.error("ResAll to Body: {}", ((WebClientResponseException) e).getResponseBodyAsString());
//                        return Mono.error(new CustomRequestException(API01.name(), API01.getMessage(), e));
//                    } else {
                        // 그 외 예외
                        log.error("ResAll API Unexpected Error ::::: {}", e.getMessage());
                        return Mono.error(new CustomRequestException(API99.name(), API99.getMessage(), e));
//                    }
                })
                // 동기적 (블로킹) 응답 대기
                .block();
        // String의 응답값을 Dto로 반환
        R apiResDTO = ConvertMapper.convertStringToDTO(json, apiResDtoClass);
        log.info("############ ResAll to Body CONVERTED RES RETURN ############");
        return apiResDTO;
    }
}
