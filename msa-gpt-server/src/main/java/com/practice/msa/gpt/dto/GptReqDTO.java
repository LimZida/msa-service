package com.practice.msa.gpt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * title : GptReqDTO
 *
 * description : GPT에게 보내는 요청값
 *    예시)
 * {
 *     "model": "gpt-3.5-turbo",
 *     "messages": [
 *         {
 *             "role": "user",
 *             "content": "현재 한국의 온도는 어떤가요?"
 *         }
 *     ],
 *     "temperature": 1,
 *     "max_tokens": 256,
 *     "top_p": 1,
 *     "frequency_penalty": 0,
 *     "presence_penalty": 0
 * }
 *
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // snake 자동변환
@Setter
@NoArgsConstructor
public class GptReqDTO {
    private String model;
    private List<message> messages;
    private int temperature = 1;
    private int maxTokens = 256;
    private int topP = 1;
    private int frequencyPenalty = 0;
    private int presencePenalty = 0;

    public GptReqDTO(String model
            , List<String> questionList
    ) {
        this.model = model;
        this.messages = questionList.stream()
                .map(question -> new message("user", question))
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class message{ // 내부 클래스를 static으로 선언
        private String role;
        private String content;
    }
}
