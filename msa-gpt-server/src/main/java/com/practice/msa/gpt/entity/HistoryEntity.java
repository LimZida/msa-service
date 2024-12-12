package com.practice.msa.gpt.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)// 기본생성자
public class HistoryEntity {
    private int seq;
    private String title;
    private String date;
    private String id;
}
