package com.practice.msa.drug.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.common.util.CommonRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

import static com.practice.msa.common.enums.ErrorEnum.LGC01;
import static com.practice.msa.common.enums.ErrorEnum.LGC04;

/**
 * title : AllResDTO
 *
 * description : 마약 제조, 수입, 수출에 대한 사용자 응답값
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
@AllArgsConstructor
public class AllResDTO{
    // 취급년도
    private Map<String, Object> all;
}
