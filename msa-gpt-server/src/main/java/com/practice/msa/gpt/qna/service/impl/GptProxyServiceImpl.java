package com.practice.msa.gpt.qna.service.impl;

import com.practice.msa.gpt.common.util.LogUtil;
import com.practice.msa.gpt.qna.dto.AnswerDTO;
import com.practice.msa.gpt.qna.dto.QuestionDTO;
import com.practice.msa.gpt.qna.service.GptService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class GptProxyServiceImpl implements GptService {
    private final GptServiceImpl gptService;
    @Override
    public AnswerDTO QnA(QuestionDTO questionDTO) {
        LogUtil.requestLogging(questionDTO);

        questionDTO.validate();

        return gptService.QnA(questionDTO);
    }
}
