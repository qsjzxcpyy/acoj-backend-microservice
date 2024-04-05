package com.qsj.acojbackendjudgeservice.judge;


import com.qsj.acojbackendmodel.entity.QuestionSubmit;

public interface JudgeService {
     QuestionSubmit doJudge(long questionSubmitId);
}
