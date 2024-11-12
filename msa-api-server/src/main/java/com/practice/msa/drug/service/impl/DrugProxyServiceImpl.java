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
    public Mono<ExportResDTO> exportDrug(ExportDTO exportDTO) {
        LogUtil.requestLogging(exportDTO);
        exportDTO.validate();
        return drugService.exportDrug(exportDTO);
    }

    @Override
    public Mono<ImportResDTO> importDrug(ImportDTO importDTO) {
        LogUtil.requestLogging(importDTO);
        importDTO.validate();
        return drugService.importDrug(importDTO);
    }

    @Override
    public Mono<MfrResDTO> manufacturingDrug(MfrDTO mfrDTO) {
        LogUtil.requestLogging(mfrDTO);
        mfrDTO.validate();
        return drugService.manufacturingDrug(mfrDTO);
    }
}
