package com.qsj.acojbackendjudgeservice.judge.strategy;


import com.qsj.acojbackendmodel.codesandbox.JudgeInfo;
import com.qsj.acojbackendmodel.dto.question.JudgeCase;
import com.qsj.acojbackendmodel.entity.Question;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private QuestionSubmit questionSubmit;
    private List<JudgeCase> judgeCase;
    private Integer status;


}
