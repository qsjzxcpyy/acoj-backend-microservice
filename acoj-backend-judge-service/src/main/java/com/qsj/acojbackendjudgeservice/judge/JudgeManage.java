package com.qsj.acojbackendjudgeservice.judge;


import com.qsj.acojbackendjudgeservice.judge.strategy.DefaultJudgeStrategyImpl;
import com.qsj.acojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategyImpl;
import com.qsj.acojbackendjudgeservice.judge.strategy.JudgeContext;
import com.qsj.acojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.qsj.acojbackendmodel.codesandbox.JudgeInfo;
import org.springframework.stereotype.Service;

@Service
public class JudgeManage{
    public JudgeInfo doJudge(JudgeContext judgeContext){
         String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if(language.equals("java")){
            judgeStrategy = new JavaLanguageJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);

    }
}
