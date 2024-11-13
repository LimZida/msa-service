package com.practice.msa.drug.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.practice.msa.common.config.ApiConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * title : ApiReqDTO
 *
 * description : 공공데이터 포탈로 보내는 요청값
 *
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
public class ApiReqDTO {
    private String serviceKey;
    private int pageNo;
    private int numOfRows;
    private String type;
    @JsonProperty("TRMT_YR")
    private String trmtYr;
}
