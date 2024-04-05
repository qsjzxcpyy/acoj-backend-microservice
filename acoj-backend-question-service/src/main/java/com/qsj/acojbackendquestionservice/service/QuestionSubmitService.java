package com.qsj.acojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qsj.acojbackendmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.qsj.acojbackendmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import com.qsj.acojbackendmodel.entity.User;
import com.qsj.acojbackendmodel.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author qsj
* @description 针对表【question_submit(判题任务表)】的数据库操作Service
* @createDate 2024-03-09 16:21:50
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser , HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage,User loginUser, HttpServletRequest request);


    Boolean deleteSubmitByQuestionId(Long questonId);
}
