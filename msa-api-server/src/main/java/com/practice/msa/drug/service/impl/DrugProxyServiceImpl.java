package com.practice.msa.drug.service.impl;

import com.practice.msa.common.util.LogUtil;
import com.practice.msa.drug.dto.*;
import com.practice.msa.drug.service.DrugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * title : DrugProxyServiceImpl
 *
 * description : proxy 요청값 검증
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.11.12
 **/
@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class DrugProxyServiceImpl implements DrugService {
    private final DrugServiceImpl drugService;

    @Override
    public AllResDTO allDrug(AllDTO allDTO){
        // 요청값 로깅
        LogUtil.requestLogging(allDTO);
        // 요청값 검증
        allDTO.validate();

        return drugService.allDrug(allDTO);
    }

    @Override
    public Mono<ExportResDTO> exportDrug(ExportDTO exportDTO) {
        // 요청값 로깅
        LogUtil.requestLogging(exportDTO);
        // 요청값 검증
        exportDTO.validate();

        return drugService.exportDrug(exportDTO);
    }

    @Override
    public Mono<ImportResDTO> importDrug(ImportDTO importDTO) {
        // 요청값 로깅
        LogUtil.requestLogging(importDTO);
        // 요청값 검증
        importDTO.validate();

        return drugService.importDrug(importDTO);
    }

    @Override
    public Mono<MfrResDTO> manufacturingDrug(MfrDTO mfrDTO) {
        // 요청값 로깅
        LogUtil.requestLogging(mfrDTO);
        // 요청값 검증
        mfrDTO.validate();

        return drugService.manufacturingDrug(mfrDTO);
    }
}
