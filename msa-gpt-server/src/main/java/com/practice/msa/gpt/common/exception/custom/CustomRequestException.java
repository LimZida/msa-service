package com.practice.msa.gpt.common.exception.custom;

import lombok.Getter;

import static com.practice.msa.gpt.common.enums.CodeEnum.EMPTY;


/**
 * title : CustomRequestException
 *
 * description : Controller 요청값 예외처리 시 커스텀하여 사용할 CustomException , 404
 *
 * reference : 에러메세지 비용 줄이는 법 https://jerry92k.tistory.com/42
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@Getter
public class CustomRequestException extends RuntimeException {
    private String errorCode;
    private Exception e;

    public CustomRequestException(String errorCode, String message , Exception e) {
        super(message);
        this.errorCode = errorCode;
        this.e = e != null ? e : new Exception(EMPTY.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
