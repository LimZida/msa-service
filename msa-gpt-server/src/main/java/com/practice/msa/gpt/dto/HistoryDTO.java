package com.practice.msa.gpt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * title : HistoryDTO
 *
 * description : 사용자 -> 서버로 보내는 요청값
 *               질문 내용을 요청
 *
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@Getter
@Setter
@NoArgsConstructor
public class HistoryDTO {
    private int seq;
    private String answer;
    private String question;
    private String date;
}
