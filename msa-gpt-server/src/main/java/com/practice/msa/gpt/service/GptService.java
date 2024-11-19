package com.practice.msa.gpt.service;

import com.practice.msa.gpt.dto.*;

import java.util.List;

public interface GptService {
    AnswerDTO QnA(QuestionDTO questionDTO);
    QnaListDTO History(SearchDTO searchDTO);
}
