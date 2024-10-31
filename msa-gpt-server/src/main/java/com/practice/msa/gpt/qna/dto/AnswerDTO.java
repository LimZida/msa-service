package com.practice.msa.gpt.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * title : AnswerDTO
 *
 * description : 서버 -> 사용자로 보내는 요청값
 *               질문 내용을 응답
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
@AllArgsConstructor
public class AnswerDTO {
    private List<String> messages;
}
