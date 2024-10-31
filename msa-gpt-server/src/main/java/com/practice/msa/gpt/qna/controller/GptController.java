package com.practice.msa.gpt.qna.controller;

import com.practice.msa.gpt.common.controller.AbstractBaseController;
import com.practice.msa.gpt.common.util.LogUtil;
import com.practice.msa.gpt.common.util.ResponseVO;
import com.practice.msa.gpt.qna.dto.AnswerDTO;
import com.practice.msa.gpt.qna.dto.QuestionDTO;
import com.practice.msa.gpt.qna.service.GptService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * title : GptController
 *
 * description :
 *               정상 처리 시 ResponseEntity<ResponseVO> 응답
 *               예외 처리 시 GlobalExceptionHandler - ResponseEntity<ResponseVO> 응답
 *
 * reference : gpt 구현 : https://jypark1111.tistory.com/203
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class GptController extends AbstractBaseController {
    private final GptService gptService;

    @PostMapping("")
    public ResponseEntity<ResponseVO> QnA(QuestionDTO questionDTO){
        AnswerDTO answerDTO = gptService.QnA(questionDTO);

        LogUtil.responseLogging(answerDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseVO(HttpStatus.OK.value(), answerDTO)
                );
    }
}
