package com.qsj.acojbackendjudgeservice.judge.strategy;

import com.qsj.acojbackendmodel.codesandbox.JudgeInfo;

public interface JudgeStrategy {
    public JudgeInfo doJudge(JudgeContext judgeContext);
}
