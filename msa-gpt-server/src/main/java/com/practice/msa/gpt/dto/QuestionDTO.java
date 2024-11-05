package com.practice.msa.gpt.dto;

import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.common.util.AbstractDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * title : QnaDTO
 *
 * description : 사용자 -> 서버로 보내는 요청값
 *               질문 내용을 요청
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
@NoArgsConstructor
public class QuestionDTO extends AbstractDTO {
    private List<String> messages;

    @Override
    public void validate() {
        messages.stream().
                filter(String::isBlank)
                .forEach(messages -> {
                    throw new CustomRequestException(FIELD_IS_EMPTY_CODE, FIELD_IS_EMPTY_MESSAGE, null);
                });
    }
}
