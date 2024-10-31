package com.practice.msa.gpt.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.practice.msa.gpt.common.exception.custom.CustomRequestException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.practice.msa.gpt.common.enums.ErrorEnum.LGC03;

/**
 * title : ConvertMapper
 *
 * description : DTO 객체와 Entity 객체 간 변환함수
 *
 * reference : dto <-> entity 변환하기 https://velog.io/@a45hvn/Java-dto%EC%99%80-entity-%EB%B3%80%ED%99%98%ED%95%98%EA%B8%B0-3
 *             java 객체와 객체간 변환 https://sumni.tistory.com/161
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@Component
public class ConvertMapper<T> {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public ConvertMapper() {
        // 선언되지 않은 객체 무시
        // 역직렬화 -> json을 java로
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 직렬화 -> java를 json으로
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    // string -> DTO (string json , dto)
    public static <T> T convertStringToDTO(String from, Class<T> to){
        try {
            return  objectMapper.readValue(from,to);
        } catch (JsonProcessingException e){
            throw new CustomRequestException(LGC03.name(),LGC03.getMessage(),e);
        }
    }

    // to(변환 당하는 객체, 변환되는 목표 객체)
    public static <F,T> T to(F from, Class<T> to){
        return objectMapper.convertValue(from,to);
    }

    // Entity List to Dto List
    public static <S, T> List<T> toList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(entity -> to(entity, targetClass))
                .collect(Collectors.toList());
    }
}
