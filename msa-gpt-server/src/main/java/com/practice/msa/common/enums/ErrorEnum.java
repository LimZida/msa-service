package com.practice.msa.common.enums;
/**
 * title : ErrorEnum
 *
 * description : 에러 ENUM코드
 *
 * reference : ENUM 활용 예제 https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC#final_%EC%83%81%EC%88%98
 *
 *
 * author : 임현영
 *
 * date : 2024.07.10
 **/

public enum ErrorEnum {
    /*
    * GPT 요청
    * */
    GPT01("API 요청에 실패했습니다."),
    GPT02("요청 연결 시간을 초과했습니다."),
    GPT03("응답 시간을 초과했습니다."),
    GPT04("문자 발송 조회 결과가 없습니다."),
    GPT05("요청한 PID가 존재하지 않아 삭제에 실패했습니다."),
    GPT06("요청한 핸드폰 등록에 실패했습니다."),
    GPT07("해당 모델 번호가 이미 존재합니다."),
    GPT98("API 요청 중 알 수 없는 오류가 발생했습니다."),
    GPT99("API 연동 중 알 수 없는 오류가 발생했습니다."),

    /*
    * 공통
    * */
    DATA01("DB 데이터 조회 중 오류가 발생했습니다."),
    DATA02("DB 데이터 리스트 조회 중 오류가 발생했습니다."),
    DATA03("DB 데이터 삽입 중 오류가 발생했습니다."),
    DATA04("DB 데이터 갱신 중 오류가 발생했습니다."),
    DATA05("DB 데이터 삭제 중 오류가 발생했습니다."),
    DATA99("DB 처리 중 알 수 없는 오류가 발생했습니다."),
    LGC01("요청 필드값이 빈값입니다."),
    LGC02("로직 처리 중 NULL 예외가 발생했습니다."),
    LGC03("응답값 변환 중 예외가 발생했습니다."),
    LGC04("offset 혹은 limit이 0보다 작을 수 없습니다."),
    LGC99("로직 처리 중 알 수 없는 오류가 발생했습니다.");


    // 문자열을 저장할 필드
    private final String value;

	// 생성자
    ErrorEnum(String value) {
        this.value = value;
    }

	// Getter
    public String getMessage() {
        return value;
    }
}

