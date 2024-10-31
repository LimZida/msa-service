package com.practice.msa.gpt.qna.service.impl;

import com.practice.msa.gpt.common.config.GptConfig;
import com.practice.msa.gpt.common.util.GptApiUtil;
import com.practice.msa.gpt.qna.dto.AnswerDTO;
import com.practice.msa.gpt.qna.dto.GptReqDTO;
import com.practice.msa.gpt.qna.dto.GptResDTO;
import com.practice.msa.gpt.qna.dto.QuestionDTO;
import com.practice.msa.gpt.qna.entity.GptResEntity;
import com.practice.msa.gpt.qna.repository.GptRepository;
import com.practice.msa.gpt.qna.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptServiceImpl implements GptService {
    private final GptRepository gptRepository;
    private final GptConfig gptConfig;
    private final GptApiUtil gptApiUtil;

    @Override
    @Transactional
    public AnswerDTO QnA(QuestionDTO questionDTO) {
        // Client Req -> Gpt Req 변환
        final String model = gptConfig.getModel();
        final List<String> messageList = questionDTO.getMessages();
        final GptReqDTO gptReqDTO = new GptReqDTO(model, messageList);

        // GPT API 요청 및 DTO 변환
        final GptResDTO gptResDTO = gptApiUtil.sendMessage(gptReqDTO);
        List<String> answerList = gptResDTO.getChoices().stream()
                .map(choice -> choice.getMessage().getContent())
                .collect(Collectors.toList());


        // DTO -> Entity 변환
        final String object = gptResDTO.getObject();
        final String resModel = gptResDTO.getModel();
        final List<GptResEntity> gptEntityList = answerList.stream()
                .map(content -> {
                    final GptResEntity gptResEntity = new GptResEntity(object,resModel,content);
                    return gptResEntity;
                })
                .collect(Collectors.toList());

        // DB log insert
        gptRepository.insertGptLog(gptEntityList);

        return new AnswerDTO(answerList);
    }
}
