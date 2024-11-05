package com.practice.msa.gpt.service.impl;

import com.practice.msa.gpt.dto.QuestionDTO;
import com.practice.msa.gpt.service.GptService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * title : GptServiceImplTest
 *
 * description : Gpt API과의 통신 지원, 동기통신 test
 *
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@SpringBootTest
@Slf4j
class GptServiceImplTest {
    @Autowired
    private GptService gptService;

    List<String> messages = new ArrayList<>();
    @BeforeEach
    void setUp() {
        messages.add("피자에 대해 짧게 설명해주세요.");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void qnA() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setMessages(messages);
        gptService.QnA(questionDTO);
    }
}