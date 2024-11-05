package com.practice.msa.welcome.service.impl;

import com.practice.msa.welcome.repository.WelcomeRepository;
import com.practice.msa.welcome.service.WelcomeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * title : WelcomeServiceImpl
 *
 * description :
 *
 * reference :
 *
 * author : 임현영
 *
 * date : 2024.10.25
 **/
@Service
@AllArgsConstructor
@Slf4j
public class WelcomeServiceImpl implements WelcomeService {
    private final WelcomeRepository welcomeRepository;

    @Override
    @Transactional(readOnly = true)
    public void welcome() {
        String result = welcomeRepository.selectWelcomeMessage();
        log.info("WelcomeServiceImpl db result: {}", result);
    }
}
