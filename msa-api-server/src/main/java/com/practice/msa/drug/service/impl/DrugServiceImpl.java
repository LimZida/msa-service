package com.practice.msa.drug.service.impl;

import com.practice.msa.common.util.ApiUtil;
import com.practice.msa.common.util.ConvertMapper;
import com.practice.msa.common.util.UrlPrefixUtil;
import com.practice.msa.drug.dto.*;
import com.practice.msa.drug.service.DrugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
/**
 * title : DrugServiceImpl
 *
 * description : Service 검증
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.11.13
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DrugServiceImpl extends UrlPrefixUtil implements DrugService {
    private final ApiUtil apiUtil;

    @Override
    public AllResDTO allDrug(AllDTO allDTO){
        final ApiReqDTO apiReqDTO = ConvertMapper.to(allDTO, ApiReqDTO.class);

        // 비동기 통신으로 수출, 수입, 제조현황 조회 진행
        Mono<ExportResDTO> exportResDTOMono = apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, DRUG_EXPORT);
        Mono<ImportResDTO> importResDTOMono = apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ImportResDTO.class, DRUG_IMPORT);
        Mono<MfrResDTO> mfrResDTOMono = apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, MfrResDTO.class, DRUG_MFR);

        // 3개의 비동기 통신을 할당 시까지 블로킹
        Map<String, Object> allInfo = Mono.zip(exportResDTOMono, importResDTOMono, mfrResDTOMono)
                .map(val -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("export", val.getT1());
                    map.put("import", val.getT2());
                    map.put("mfr", val.getT3());
                    return map;
                })
                .block();

        return new AllResDTO(allInfo);
    }
    @Override
    public Mono<ExportResDTO> exportDrug(ExportDTO exportDTO) {
        final ApiReqDTO apiReqDTO = ConvertMapper.to(exportDTO, ApiReqDTO.class);

        // 수출 비동기 통신 연습
        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, DRUG_EXPORT).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, DRUG_EXPORT);
    }

    @Override
    public Mono<ImportResDTO> importDrug(ImportDTO importDTO) {
        final ApiReqDTO apiReqDTO = ConvertMapper.to(importDTO, ApiReqDTO.class);

        // 수입 현황 비동기 통신 연습
        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ImportResDTO.class, DRUG_IMPORT).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ImportResDTO.class, DRUG_IMPORT);
    }

    @Override
    public Mono<MfrResDTO> manufacturingDrug(MfrDTO mfrDTO) {
        final ApiReqDTO apiReqDTO = ConvertMapper.to(mfrDTO, ApiReqDTO.class);

        // 제조 현황 비동기 통신 연습
        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, MfrResDTO.class, DRUG_MFR).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, MfrResDTO.class, DRUG_MFR);
    }
}
