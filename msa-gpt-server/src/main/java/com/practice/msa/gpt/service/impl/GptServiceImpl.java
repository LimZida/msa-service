package com.practice.msa.gpt.service.impl;

import com.practice.msa.common.config.GptConfig;
import com.practice.msa.common.util.CommonRequestDTO;
import com.practice.msa.common.util.ConvertMapper;
import com.practice.msa.common.util.GptApiUtil;
import com.practice.msa.gpt.dto.*;
import com.practice.msa.gpt.entity.GptResEntity;
import com.practice.msa.gpt.entity.HistoryDetailEntity;
import com.practice.msa.gpt.entity.HistoryEntity;
import com.practice.msa.gpt.entity.SearchEntity;
import com.practice.msa.gpt.repository.GptRepository;
import com.practice.msa.gpt.service.GptService;
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
        final String prompt = gptReqDTO.getMessages().get(0).getContent();

        // GPT API 요청 및 DTO 변환
        final GptResDTO gptResDTO = gptApiUtil.sendMessageAndResAll(GptResDTO.class ,gptReqDTO);
        List<String> answerList = gptResDTO.getChoices().stream()
                .map(choice -> choice.getMessage().getContent())
                .collect(Collectors.toList());


        // DTO -> Entity 변환
        final String object = gptResDTO.getObject();
        final String resModel = gptResDTO.getModel();
        final List<GptResEntity> gptEntityList = answerList.stream()
                .map(content -> {
                    final GptResEntity gptResEntity = new GptResEntity(object,resModel,content,prompt);
                    return gptResEntity;
                })
                .collect(Collectors.toList());

        // DB log insert
        gptRepository.insertGptLog(gptEntityList);

        return new AnswerDTO(answerList);
    }

    @Override
    @Transactional(readOnly = true)
    public QnaListDTO HistoryDetail(SearchDTO searchDTO) {
        SearchEntity searchEntity = ConvertMapper.to(searchDTO, SearchEntity.class);
        List<HistoryDetailEntity> historyDetailList = gptRepository.selectGptHistoryDetail(searchEntity);

        return new QnaListDTO(ConvertMapper.toList(historyDetailList, HistoryDetailDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryListDTO History(CommonRequestDTO commonRequestDTO) {
        SearchEntity searchEntity = ConvertMapper.to(commonRequestDTO, SearchEntity.class);
        List<HistoryEntity> historyList = gptRepository.selectGptHistory(searchEntity);

        return new HistoryListDTO(ConvertMapper.toList(historyList, HistoryDTO.class));
    }
}
