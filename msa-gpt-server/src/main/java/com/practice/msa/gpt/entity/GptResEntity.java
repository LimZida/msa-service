package com.practice.msa.gpt.entity;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)// 기본생성자
public class GptResEntity {
    private final String object;
    private final String model;
    private final String content;
}
