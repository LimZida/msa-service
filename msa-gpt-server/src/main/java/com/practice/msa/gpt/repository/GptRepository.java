package com.practice.msa.gpt.repository;

import com.practice.msa.gpt.entity.GptResEntity;
import com.practice.msa.gpt.entity.HistoryDetailEntity;
import com.practice.msa.gpt.entity.HistoryEntity;
import com.practice.msa.gpt.entity.SearchEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GptRepository {
    int insertGptLog(List<GptResEntity> gptResEntityList);
    List<HistoryDetailEntity> selectGptHistoryDetail(SearchEntity searchEntity);
    List<HistoryEntity> selectGptHistory(SearchEntity searchEntity);
}
