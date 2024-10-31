package com.practice.msa.gpt.common.util;

import com.practice.msa.gpt.common.exception.custom.CustomRequestException;
import lombok.extern.slf4j.Slf4j;

import static com.practice.msa.gpt.common.enums.ErrorEnum.LGC03;


/**
 * title : LogUtil
 *
 * description : 공통 로깅 시 사용하는 유틸, 객체 넣어주면 됩니다.
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.07.24
 **/
@Slf4j
public abstract class LogUtil {
    public static <T extends Object> void responseLogging(T message){
        log.info(">>>>>> 응답값 : {}", prettyPrinter(message));
    }

    public static <T extends Object> void responseErrorLogging(T message){
        log.error(">>>>>> 에러 응답값 : {}", prettyPrinter(message));
    }

    public static <T extends Object> void requestLogging(T message){
        log.info(">>>>>> 요청값 : {}", prettyPrinter(message));
    }

    // 응답값 틀 만들어주는 함수
    // ex)
    //    {
    //      "resultCd" : "SIGN00",
    //      "resultMsg" : "정상 로그인 되었습니다.",
    //      "userId" : "mcncadmin",
    //      "passwd" : "1111",
    //      "name" : "관리자",
    //      "aclId" : "1",
    //      "phone" : "",
    //      "useYN" : "Y",
    //      "regDate" : "20070207",
    //      "groupName" : "시스템관리자",
    //      "failCnt" : 0
    //    }
    private static <T extends Object> String prettyPrinter(T message) {
        try {
            return ConvertMapper.objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(message);
        } catch (Exception e) {
            throw new CustomRequestException(LGC03.name(),LGC03.getMessage(),e);
        }
    }
}
