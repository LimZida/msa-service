package com.practice.msa.drug.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * title : MfrResDTO
 *
 * description : 마약 제조 현황에 대한 응답값
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
public class MfrResDTO {
    private int pageNo;
    private int totalCount;
    private int numOfRows;
    private List<ItemDTO> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class)  // JSON 필드명을 snakeCase로 자동 변환
    public static class ItemDTO {
        private String trmtYm;
        private String narkDivsNm;
        private String pamistNum;
        private String prdlstNum;
        private String qty;
        private String amt;
    }
}
