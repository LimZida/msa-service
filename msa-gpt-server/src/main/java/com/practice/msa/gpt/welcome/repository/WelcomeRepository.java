package com.practice.msa.gpt.welcome.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * title : WelcomeRepository
 *
 * description :
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.25
 **/
@Mapper
@Repository
public interface WelcomeRepository {
    String selectWelcomeMessage();
}
