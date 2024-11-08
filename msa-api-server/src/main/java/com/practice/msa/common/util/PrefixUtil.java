package com.practice.msa.common.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * title : PrefixUtil
 *
 * description : 요청 시 properties 값 내 prefix 선언
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.08.26
 **/
public abstract class PrefixUtil {
    @Value("${data.api.drug.import.performance.prefix}")
    protected String DRUG_IMPORT;
    @Value("${data.api.drug.export.performance.prefix}")
    protected String DRUG_EXPORT;
    @Value("${data.api.drug.manufacturing.performance.prefix}")
    protected String DRUG_MFR;
}
