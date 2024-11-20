package com.practice.msa.gpt.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)// 기본생성자
public class HistoryEntity {
//    private final int seq;
    private final String answer;
    private final String question;
    private final String date;
}
