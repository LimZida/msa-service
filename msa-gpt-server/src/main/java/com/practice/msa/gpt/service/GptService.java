package com.practice.msa.gpt.service;

import com.practice.msa.common.util.CommonRequestDTO;
import com.practice.msa.gpt.dto.*;

public interface GptService {
    AnswerDTO QnA(QuestionDTO questionDTO);
    QnaListDTO HistoryDetail(SearchDTO searchDTO);
    HistoryListDTO History(CommonRequestDTO commonRequestDTO);
}
