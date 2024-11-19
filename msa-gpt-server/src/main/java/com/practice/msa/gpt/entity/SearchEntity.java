package com.practice.msa.gpt.entity;

import lombok.*;

/**
 * title : UserInfoDTO
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
@AllArgsConstructor
@NoArgsConstructor
public class SearchEntity {
    private String userId;
    private int limit;
    private int offset;
}
