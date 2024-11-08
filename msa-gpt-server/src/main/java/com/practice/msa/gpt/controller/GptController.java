package com.practice.msa.gpt.controller;

import com.practice.msa.common.controller.AbstractBaseController;
import com.practice.msa.common.util.LogUtil;
import com.practice.msa.common.util.ResponseVO;
import com.practice.msa.gpt.dto.AnswerDTO;
import com.practice.msa.gpt.dto.QuestionDTO;
import com.practice.msa.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @PostMapping("/question")
    public ResponseEntity<ResponseVO> QnA(@RequestBody QuestionDTO questionDTO){
        AnswerDTO answerDTO = gptService.QnA(questionDTO);

        LogUtil.responseLogging(answerDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseVO(HttpStatus.OK.value(), answerDTO)
                );
    }
}
