package com.practice.msa.gpt.repository;

import com.practice.msa.gpt.entity.GptResEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GptRepository {
    int insertGptLog(List<GptResEntity> gptResEntityList);
}
