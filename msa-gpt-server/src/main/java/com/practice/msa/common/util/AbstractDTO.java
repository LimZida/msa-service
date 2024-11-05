package com.practice.msa.common.util;


import com.practice.msa.common.exception.custom.CustomRequestException;

/**
 * title : FieldCheckUtil
 *
 * description : DTO Entity VO 등에 쓰이며 field에 대한 validate 함수 강제
 *
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.08.07
 **/
public abstract class AbstractDTO {
    protected static final String FIELD_IS_EMPTY_CODE = "LGC01";
    protected static final String FIELD_IS_EMPTY_MESSAGE = "요청 필드값이 빈값입니다.";

    protected void requestException(){
        throw new CustomRequestException(FIELD_IS_EMPTY_CODE, FIELD_IS_EMPTY_MESSAGE, null);
    }

    // 구현 강제
    public abstract void validate();
}
