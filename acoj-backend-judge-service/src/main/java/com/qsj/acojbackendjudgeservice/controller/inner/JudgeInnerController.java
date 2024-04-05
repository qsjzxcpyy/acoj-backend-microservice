package com.qsj.acojbackendjudgeservice.controller.inner;

import com.qsj.acojbackendjudgeservice.judge.JudgeService;
import com.qsj.acojbackendmodel.entity.Question;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import com.qsj.acojbackendserviceclient.service.JudgeFeignClient;
import com.qsj.acojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 该服务进内部调用
 */
@RestController
@RequestMapping("/inner")

public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    JudgeService judgeService;

    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}

