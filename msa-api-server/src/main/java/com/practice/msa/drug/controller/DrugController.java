package com.practice.msa.drug.controller;

import com.practice.msa.common.controller.AbstractBaseController;
import com.practice.msa.common.util.ConvertMapper;
import com.practice.msa.common.util.LogUtil;
import com.practice.msa.common.util.ResponseVO;
import com.practice.msa.drug.dto.*;
import com.practice.msa.drug.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * title : DrugController
 *
 * description : 식품의약품안전처 마약류 수출/수입/제조 조회 Controller
 *
 *               정상 처리 시 ResponseEntity<ResponseVO> 응답
 *               예외 처리 시 GlobalExceptionHandler - ResponseEntity<ResponseVO> 응답
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.29
 **/
@RestController
@RequestMapping("/api/v1/drug")
@RequiredArgsConstructor
public class DrugController{
    private final DrugService drugService;

    // 식품의약품안전처_의료용 마약류 연간 수출/수입/제조 실적 서비스 전부 조회
    @GetMapping("/all")
    public ResponseEntity<ResponseVO> allDrug(ExportDTO exportDTO){


        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseVO(HttpStatus.OK.value(), "")
                );
    }

    // 식품의약품안전처_의료용 마약류 연간 수출실적 서비스
    @GetMapping("/export")
    public Mono<ResponseEntity<ResponseVO>> exportDrug(ExportDTO exportDTO){
//        Mono<ExportResDTO> exportResDTOMono = drugService.exportDrug(exportDTO);

        return drugService.exportDrug(exportDTO)
            .map(data ->
                    ResponseEntity.status(HttpStatus.OK)
                    .body(
                        new ResponseVO(HttpStatus.OK.value(), data)
                    )
            );
    }

     // 식품의약품안전처_의료용 마약류 연간 수입실적 서비스
    @GetMapping("/import")
    public Mono<ResponseEntity<ResponseVO>> importDrug(ImportDTO importDTO) {
        return drugService.importDrug(importDTO)
            .map(data ->
                ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseVO(HttpStatus.OK.value(), data))
            );
    }

    // 식품의약품안전처_의료용 마약류 연간 제조실적 서비스
    @GetMapping("/manufacturing")
    public Mono<ResponseEntity<ResponseVO>> manufacturingDrug(MfrDTO mfrDTO) {
        return drugService.manufacturingDrug(mfrDTO)
            .map(data ->
                ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseVO(HttpStatus.OK.value(), data))
            );
    }
}
