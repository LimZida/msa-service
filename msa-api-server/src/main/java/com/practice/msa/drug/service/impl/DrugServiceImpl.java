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

@Service
@Slf4j
@RequiredArgsConstructor
public class DrugServiceImpl extends UrlPrefixUtil implements DrugService {
    private final ApiUtil apiUtil;

    @Override
    public Mono<ExportResDTO> exportDrug(ExportDTO exportDTO) {
        final ApiReqDTO apiReqDTO = ConvertMapper.to(exportDTO, ApiReqDTO.class);

        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, DRUG_EXPORT).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ExportResDTO.class, DRUG_EXPORT);
    }

    @Override
    public Mono<ImportResDTO> importDrug(ImportDTO importDTO) {
        final ApiReqDTO apiReqDTO = ConvertMapper.to(importDTO, ApiReqDTO.class);

        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ImportResDTO.class, DRUG_IMPORT).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, ImportResDTO.class, DRUG_IMPORT);
    }

    @Override
    public Mono<MfrResDTO> manufacturingDrug(MfrDTO mfrDTO) {
        ApiReqDTO apiReqDTO = ConvertMapper.to(mfrDTO, ApiReqDTO.class);

        for(int i = 0; i<5; ++i){
            apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, MfrResDTO.class, DRUG_MFR).subscribe();
        }

        return apiUtil.sendGetMessageAndResAllByAsync(apiReqDTO, MfrResDTO.class, DRUG_MFR);
    }
}
