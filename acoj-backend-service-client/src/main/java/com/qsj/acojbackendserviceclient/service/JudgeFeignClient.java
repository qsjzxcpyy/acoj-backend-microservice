package com.qsj.acojbackendserviceclient.service;


import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "acoj-backend-judge-service", path = "/api/judge/inner")


public interface JudgeFeignClient {
     @PostMapping("/do")
     QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
