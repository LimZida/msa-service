package com.practice.msa.common.exception.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * title : GlobalExceptionVO
 *
 * description : 에러 시 ResponseEntity Body에 들어갈 ErrorDto
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class GlobalExceptionVO {
    private final String errorCode;
    private final String errorMessage;
    private final String errorDetailMessage;
}
