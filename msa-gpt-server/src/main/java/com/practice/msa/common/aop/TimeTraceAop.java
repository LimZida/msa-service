package com.practice.msa.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * title : TimeTraceAop
 *
 * description : 함수 실행 시간에 대해 AOP 적용
 *
 * reference :  시간 aop : https://hseungyeon.tistory.com/349
 *              aop 어노테이션 : https://programforlife.tistory.com/107 , https://code-lab1.tistory.com/193
 *
 * author : 임현영
 * date : 2024.10.25
 **/
@Slf4j
@Aspect
@Component
public class TimeTraceAop {
    // 공통관심사항을 적용할 곳, Controller만 타겟팅
    @Around("execution(* com.practice.msa.gpt..controller..*(..))")

    /*
    * 매 함수의 실행시간을 체크하는 기능
    * */
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();    // 시작 시각
        Exception exception = null;
        try {
            return joinPoint.proceed();
        }
        catch (Exception e){
            exception = e;

            //globalException Handler로 에러 위임
            throw e;
        }
        finally {
            getLogging(joinPoint,start, exception);
        }
    }

    private void getLogging(JoinPoint joinPoint, long start, Exception e) {
        long finish = System.currentTimeMillis();// 종료 시각
        long timeMs = 0;
        // 에러 응답
        if(e != null){
            timeMs = start - finish;   // 호출 시간 반대로
            log.error(" -------------------------------------------------------------------------------");
            log.error("ㅣ오류 실행 함수 : {}", getMethod(joinPoint));
            log.error("ㅣ오류 서비스 수행시간 : {}", timeMs + "ms");
            // uncheckException일 경우 override한 커스텀 fillInStackTrace 호출
            if(e instanceof RuntimeException){
                log.error("ㅣ오류 내용 : ",e.fillInStackTrace());
            } else{
                log.error("ㅣ오류 내용 : ",e.getMessage());
            }
            log.error(" -------------------------------------------------------------------------------");
        }
        // 정상 응답
        else{
            timeMs = finish - start;   // 호출 시간
            log.info(" -------------------------------------------------------------------------------");
            log.info("ㅣ정상 실행 함수 : {}", getMethod(joinPoint));
            log.info("ㅣ정상 서비스 수행시간 : {}", timeMs + "ms");
            log.info(" -------------------------------------------------------------------------------");
        }

        log.info("");
        log.info(">>>>>>>>>>>>>>>> Response End <<<<<<<<<<<<<<<<<");
        log.info("");
    }

    // JoinPoint로 메서드 정보 가져오기
    private String getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.toShortString();
    }
}