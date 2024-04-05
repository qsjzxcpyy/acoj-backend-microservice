package com.qsj.acojbackendquestionservice.controller.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qsj.acojbackendcommon.common.ErrorCode;
import com.qsj.acojbackendcommon.exception.BusinessException;
import com.qsj.acojbackendmodel.dto.question.QuestionQueryRequest;
import com.qsj.acojbackendmodel.entity.Question;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import com.qsj.acojbackendquestionservice.service.QuestionService;
import com.qsj.acojbackendquestionservice.service.QuestionSubmitService;
import com.qsj.acojbackendserviceclient.service.QuestionFeignClient;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 该服务进内部调用
 */
@RestController
@RequestMapping("/inner")

public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    QuestionService questionService;

    @Resource
    QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("qustionId") long qustionId) {
        return questionService.getById(qustionId);
    }

    @Override
    @GetMapping("/question_submit/get/ids")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("qustionSubmitId") long qustionSubmitId) {
        return questionSubmitService.getById(qustionSubmitId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public Boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

    @Override
    @PostMapping("/update")
    public Boolean updateById(@RequestBody Question question) {
        return questionService.updateById(question);
    }

    @Override
    @PostMapping("/delete/byUserId")
    public Boolean deleteById(@RequestBody Long userId) {
        if(ObjectUtils.isEmpty(userId) || userId < 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        QuestionQueryRequest request = new QuestionQueryRequest();
        request.setUserId(userId);
        QueryWrapper<Question> queryWrapper = questionService.getQueryWrapper(request);
        List<Question> list = questionService.list(queryWrapper);
        for(int i = 0; i < list.size() ; i ++){
            Question question = list.get(i);
            long id = question.getId();
            questionSubmitService.deleteSubmitByQuestionId(id);
            questionService.removeById(id);
        }
        return  true;
    }
}

