package com.practice.msa.gpt.common.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * title : ResponseVO
 *
 * description : Success / Fail Response 결과 반환
 *               불변객체로 사용
 *
 * reference :
 *
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseVO<T> {
    private final int code;
    private final T data;
}
