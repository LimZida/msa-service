package com.practice.msa.gpt.service;

import com.practice.msa.gpt.dto.QuestionDTO;
import com.practice.msa.gpt.dto.AnswerDTO;

public interface GptService {
    AnswerDTO QnA(QuestionDTO questionDTO);
}
