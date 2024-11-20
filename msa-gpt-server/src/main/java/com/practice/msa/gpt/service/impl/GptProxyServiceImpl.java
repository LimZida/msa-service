package com.practice.msa.gpt.service.impl;

import com.practice.msa.common.util.CommonRequestDTO;
import com.practice.msa.common.util.LogUtil;
import com.practice.msa.gpt.dto.*;
import com.practice.msa.gpt.service.GptService;
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

    @Override
    public QnaListDTO HistoryDetail(SearchDTO searchDTO) {
        LogUtil.requestLogging(searchDTO);

        searchDTO.validate();

        return gptService.HistoryDetail(searchDTO);
    }

    @Override
    public HistoryListDTO History(CommonRequestDTO commonRequestDTO) {
        LogUtil.requestLogging(commonRequestDTO);

        commonRequestDTO.validate();

        return gptService.History(commonRequestDTO);
    }
}
