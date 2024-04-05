package com.qsj.acojbackendquestionservice.service;

import com.qsj.acojbackendquestionservice.service.impl.QuestionSubmitServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestService {
    @Resource
    QuestionSubmitService questionSubmitService;

    @Test
    public void d(){
        questionSubmitService.deleteSubmitByQuestionId(1774817877829480450L);
    }
}
