package com.practice.msa.gpt.common.util;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * title : CommonResponseDTO
 *
 * description : 공통적으로 사용할 CommonResponseDTO, 딱히 응답값 없을때 그냥 메세지용이나 상속받아 사용
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.07.25
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 기본생성자
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommonResponseDTO {
	protected String resultCd;
	protected String resultMsg;
}


