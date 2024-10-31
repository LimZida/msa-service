package com.practice.msa.gpt.qna.service;

import com.practice.msa.gpt.qna.dto.AnswerDTO;
import com.practice.msa.gpt.qna.dto.QuestionDTO;

public interface GptService {
    AnswerDTO QnA(QuestionDTO questionDTO);
}
