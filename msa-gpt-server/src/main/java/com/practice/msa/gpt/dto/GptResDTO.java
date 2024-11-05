package com.practice.msa.gpt.dto;

import lombok.*;

import java.util.List;
/**
 * title : GptResDTO
 *
 * description : GPT에게 오는 응답값, 이 중 choices 필드만 사용
 *
 *    예시)
 * {
 *     "id": "chatcmpl-8tUbLbfne4rO96nVrfjguN0yzp0F8",
 *     "object": "chat.completion",
 *     "created": 1708236363,
 *     "model": "gpt-3.5-turbo-0125",
 *     "choices": [
 *         {
 *             "index": 0,
 *             "message": {
 *                 "role": "assistant",
 *                 "content": "죄송합니다, 현재 저는 실시간 정보를 제공할 수 없습니다. 하지만 한국의 온도 정보는 기상청 홈페이지나 기상앱을 통해 확인하실 수 있습니다.더 필요한 정보가 있으시면 도와드리겠습니다."
 *             },
 *             "logprobs": null,
 *             "finish_reason": "stop"
 *         }
 *     ],
 *     "usage": {
 *         "prompt_tokens": 25,
 *         "completion_tokens": 82,
 *         "total_tokens": 107
 *     },
 *     "system_fingerprint": "fp_69829325d0"
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
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GptResDTO {
    private String created;
    private String object;
    private String model;
    private List<Choice> choices;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        //gpt 대화 인덱스 번호
        private int index;
        // 지피티로 부터 받은 메세지
        // 여기서 content는 유저의 prompt가 아닌 gpt로부터 받은 response
        private Message message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
