package com.practice.msa.drug.service;

import com.practice.msa.drug.dto.*;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface DrugService {
    AllResDTO allDrug(AllDTO allDTO);
    Mono<ExportResDTO> exportDrug(ExportDTO exportDTO);
    Mono<ImportResDTO> importDrug(ImportDTO importDTO);
    Mono<MfrResDTO> manufacturingDrug(MfrDTO mfrDTO);
}
