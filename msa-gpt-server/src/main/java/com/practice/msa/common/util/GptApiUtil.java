package com.practice.msa.common.util;

import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.gpt.dto.GptReqDTO;
import com.practice.msa.gpt.dto.GptResDTO;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import static com.practice.msa.common.enums.ErrorEnum.*;

/**
 * title : GptApiUtil
 *
 * description : Gpt API과의 통신 지원, 동기통신으로 진행
 *
 *
 * reference : gpt 구현 예제 : https://jypark1111.tistory.com/203
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
// retry 방법 1
//@EnableRetry
public class GptApiUtil {
    private final WebClient webClient;

//    retry 방법 1
//    @Retryable(
//        value = {CustomRequestException.class},
//        maxAttempts = 3,
//        backoff = @Backoff(delay = 1000)
//    )

    /*
    *  body 응답만 받을 경우
    * */
    public <T,R> R sendMessageAndResBody(Class<R> gptResDTOClass , T gptReqDTO){
        log.info("############ GPT API REQUEST ############");
        LogUtil.requestLogging(gptReqDTO);

        final String json = webClient.post()
                .bodyValue(gptReqDTO)
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
                    return Mono.error(new CustomRequestException(GPT99.name(), GPT99.getMessage(), e));
                })
                // 정상 응답
                .doOnNext(res ->{
                    log.info("############ GPT API RETURN ############");
                    log.info(res);
                })
                // 동기적 (블로킹) 응답 대기
                .block();

        final R gptResDTO = ConvertMapper.convertStringToDTO(json,gptResDTOClass);
        log.info("############ CONVERTED RES RETURN ############");
        LogUtil.responseLogging(gptResDTO);
        return gptResDTO;
    }

    /*
    * header + body 응답 받을 경우
    * */
    public <T,R> R sendMessageAndResAll(Class<R> gptResDTOClass , T gptReqDTO) {
        log.info("############ ResAll GPT API REQUEST ############");
        LogUtil.requestLogging(gptReqDTO)
        ;
        AtomicLong t1 = new AtomicLong();
        AtomicLong t2 = new AtomicLong();

        t1.set(System.currentTimeMillis());

        final String json = webClient.post()
                .bodyValue(gptReqDTO)
                // Mono 방식으로 변환
                .exchangeToMono(response -> {
                    t2.set(System.currentTimeMillis());
                    log.info("API TIME : {}ms", t2.get() -t1.get());

                    log.info("############ GPT API RESPONSE ############");
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
                    // 그 외 예외
                    log.error("ResAll API Unexpected Error ::::: {}", e.getMessage());
                    return Mono.error(new CustomRequestException(GPT99.name(), GPT99.getMessage(), e));
                })
                // 동기적 (블로킹) 응답 대기
                .block();

        final R gptResDTO = ConvertMapper.convertStringToDTO(json,gptResDTOClass);
        log.info("############ ResAll to Body CONVERTED RES RETURN ############");
        LogUtil.responseLogging(gptResDTO);
        return gptResDTO;
    }
}
