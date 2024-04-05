package com.qsj.acojbackendserviceclient.service;


import com.qsj.acojbackendcommon.annotation.AuthCheck;
import com.qsj.acojbackendcommon.common.BaseResponse;
import com.qsj.acojbackendcommon.constant.UserConstant;
import com.qsj.acojbackendmodel.dto.question.QuestionUpdateRequest;
import com.qsj.acojbackendmodel.entity.Question;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 题目服务
 */
@FeignClient(name = "acoj-backend-question-service", path = "/api/question/inner")

public interface QuestionFeignClient {
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("qustionId") long qustionId);


    @GetMapping("/question_submit/get/ids")
    QuestionSubmit getQuestionSubmitById(@RequestParam("qustionSubmitId") long qustionSubmitId);

    @PostMapping("/question_submit/update")
    Boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    @PostMapping("/update")
    Boolean updateById(@RequestBody Question question);

    @PostMapping("/delete/byUserId")
    Boolean deleteById(@RequestBody Long userId);

}
