package com.practice.msa.drug.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.msa.common.exception.custom.CustomRequestException;
import com.practice.msa.common.util.CommonRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import static com.practice.msa.common.enums.ErrorEnum.LGC01;
import static com.practice.msa.common.enums.ErrorEnum.LGC04;

/**
 * title : AllDTO
 *
 * description : 마약 제조, 수입, 수출 전부를 조회하기 위한 요청값
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
public class AllDTO extends CommonRequestDTO {
    // 취급년도
    @JsonProperty("TRMT_YR")
    private String trmtYr;

    @Override
    public void validate() {
        if(super.getPageNo() < 0 || super.getNumOfRows() < 0){
            throw new CustomRequestException(LGC04.name(), LGC04.getMessage(), null);
        }

        if(StringUtils.isBlank(trmtYr)) {
            throw new CustomRequestException(LGC01.name(), LGC01.getMessage(), null);
        }
    }
}
