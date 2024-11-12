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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
/**
 * title : DrugServiceImplTest
 *
 * description : Drug API과의 통신 지원, 동기통신 test
 *               비동기 통신 시 test코드의 경우 main이 끝나버리면 전부 끝내버리기 때문에 값을 보지 못하지 끝난다.
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
    void exportDrug() throws InterruptedException {
//        List<ExportDTO> people = Arrays.asList(
//                new ExportDTO("2024"),
//                new ExportDTO("2024"),
//                new ExportDTO("2024"),
//                new ExportDTO("2024"),
//                new ExportDTO("2024")
//        );
//
//        List<String> collect = people.stream()
//                .map(val -> val.getTrmtYr())
//                .collect(Collectors.toList());
//
//        List<String[]> collect2 = people.stream()
//                .map(val -> val.getTrmtYr().split(""))
//                .collect(Collectors.toList());
//
//        Set<String> collect3 = people.stream()
//                .map(val -> val.getTrmtYr().split(""))
//                .flatMap(Arrays::stream)
//                .collect(Collectors.toSet());
//
//        LogUtil.responseLogging(collect);
//        LogUtil.responseLogging(collect2);
//        LogUtil.responseLogging(collect3);

        // 비동기로 실행하였기에 이러한 상황이 발생한건데 test코드의 경우 main이 끝나버리면 전부 끝내버리기 때문에 값을 보지 못한 것입니다.
        // 이를 위해 코드를 아래와 같이 변경하고 다시 실행해 두 값이 나오게 됩니다.
        CountDownLatch latch = new CountDownLatch(1);
        Mono<ExportResDTO> exportResDTOMono = drugService.exportDrug(exportDTO);
        exportResDTOMono.subscribe(val -> {
            LogUtil.responseLogging(val);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void importDrug() {
    }

    @Test
    void manufacturingDrug() {
    }
}