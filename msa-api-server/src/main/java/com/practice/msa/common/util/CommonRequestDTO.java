package com.practice.msa.common.util;

import com.practice.msa.common.exception.custom.CustomRequestException;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.practice.msa.common.enums.ErrorEnum.LGC04;


/**
 * title : CommonRequestDTO
 *
 * description : 공통적으로 사용할 RequestDTO, 쓰고자 하는 DTO에 상속받아서 사용합니다.
 *
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.10.29
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonRequestDTO{
    private String type = "json";
    private int pageNo = 0;
    private int numOfRows = 10;

    // 필요하면 하위단에서 Override 해서 쓰시면 됩니다.
    public void validate() {
        if(pageNo < 0 || numOfRows < 0){
            throw new CustomRequestException(LGC04.name(), LGC04.getMessage(), null);
        }
    }
}
