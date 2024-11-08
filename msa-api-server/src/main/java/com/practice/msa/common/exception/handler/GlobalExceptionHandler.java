package com.practice.msa.common.exception.handler;

import com.practice.msa.common.exception.custom.CustomDBException;
import com.practice.msa.common.exception.custom.CustomServiceException;
import com.practice.msa.common.exception.vo.GlobalExceptionVO;
import com.practice.msa.common.util.LogUtil;
import com.practice.msa.common.enums.ErrorEnum;
import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.common.util.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * title : GlobalExceptionHandler
 *
 * description : 예외 발생 시 처리해주는 핸들러
 *               Exception - 예기치 못한 에러
 *               NullPointerException - NULL 에러
 *               CustomDBException - DB 처리 에러
 *               CustomServiceException - 로직 예외 처리
 *               CustomRequestException - 요청값 에러
 *
 *
 * reference :  Exception Handler : https://velog.io/@kiiiyeon/%EC%8A%A4%ED%94%84%EB%A7%81-ExceptionHandler%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC
 *
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 커스텀 DB 에러
    @ExceptionHandler(CustomDBException.class)
    public ResponseEntity<ResponseVO> handleDBException(CustomDBException e) {
        GlobalExceptionVO exception = getExceptionVO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception));
    }

    // 로직 처리 예외
    // 예상 가능한 정상 예외처리
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<ResponseVO> handleServiceException(CustomServiceException e) {
        GlobalExceptionVO exception = getExceptionVO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(getFailResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception));
    }

    // 요청 처리 예외
    // 예상 가능한 정상 예외처리
    @ExceptionHandler(CustomRequestException.class)
    public ResponseEntity<ResponseVO> handleRequestException(CustomRequestException e) {
        GlobalExceptionVO exception = getExceptionVO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getFailResponse(HttpStatus.BAD_REQUEST.value(),exception));
    }

    // Null 에러
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseVO> handleNullException(NullPointerException e) {
        GlobalExceptionVO exception = getExceptionVO(ErrorEnum.LGC02.name(), ErrorEnum.LGC02.getMessage(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception));
	}

    // DB 에러
//    @ExceptionHandler(PSQLException.class)
//    public ResponseEntity<ResponseVO> handleException(PSQLException e) {
//        GlobalExceptionVO exception = getExceptionVO(ErrorEnum.DATA99.name(), ErrorEnum.DATA99.getMessage(), e.getMessage());
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception));
//    }

    // 예기치 못한 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> handleException(Exception e) {
        GlobalExceptionVO exception = getExceptionVO(ErrorEnum.LGC99.name(), ErrorEnum.LGC99.getMessage(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception));
    }


    // error DTO 생성
    private GlobalExceptionVO getExceptionVO(String errorCode, String errorMessage, String errorDetailMessage){
        GlobalExceptionVO exception = new GlobalExceptionVO(errorCode, errorMessage, errorDetailMessage);
        LogUtil.responseLogging(exception);
        return exception;
    }

    // error Response 생성
    private ResponseVO getFailResponse(int statusCode, GlobalExceptionVO globalExceptionVO){
        return new ResponseVO(statusCode, globalExceptionVO);
    }
}
