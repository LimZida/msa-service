package com.practice.msa.drug.service.impl;

import com.practice.msa.common.util.LogUtil;
import com.practice.msa.drug.dto.ExportDTO;
import com.practice.msa.drug.dto.ExportResDTO;
import com.practice.msa.drug.dto.ImportDTO;
import com.practice.msa.drug.dto.MfrDTO;
import com.practice.msa.drug.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
/**
 * title : DrugServiceImplTest
 *
 * description : Drug API과의 통신 지원, 동기통신 test
 *
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.11.07
 **/
@SpringBootTest
@Slf4j
class DrugServiceImplTest {
    @Autowired
    private DrugService drugService;
    private ExportDTO exportDTO;
    private ImportDTO importDTO;
    private MfrDTO mfrDTO;

    @BeforeEach
    void setUp() {
        exportDTO = new ExportDTO();
        exportDTO.setTrmtYr("2024");
        exportDTO.setPageNo(1);
        exportDTO.setNumOfRows(10);

        importDTO = new ImportDTO();
        importDTO.setTrmtYr("2024");
        importDTO.setPageNo(1);
        importDTO.setNumOfRows(10);

        mfrDTO = new MfrDTO();
        mfrDTO.setTrmtYr("2024");
        mfrDTO.setPageNo(1);
        mfrDTO.setNumOfRows(10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void exportDrug() {
        Mono<ExportResDTO> exportResDTOMono = drugService.exportDrug(exportDTO);
        exportResDTOMono.subscribe();
        LogUtil.responseLogging(exportResDTOMono);
    }

    @Test
    void importDrug() {
    }

    @Test
    void manufacturingDrug() {
    }
}