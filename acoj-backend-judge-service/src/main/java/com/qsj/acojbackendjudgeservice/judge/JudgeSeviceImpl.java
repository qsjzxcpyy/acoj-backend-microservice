package com.qsj.acojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;

import com.qsj.acojbackendcommon.common.ErrorCode;
import com.qsj.acojbackendcommon.exception.BusinessException;
import com.qsj.acojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.qsj.acojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.qsj.acojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.qsj.acojbackendjudgeservice.judge.strategy.JudgeContext;
import com.qsj.acojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.qsj.acojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.qsj.acojbackendmodel.codesandbox.JudgeInfo;
import com.qsj.acojbackendmodel.dto.question.JudgeCase;
import com.qsj.acojbackendmodel.entity.Question;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import com.qsj.acojbackendmodel.enums.JudgeInfoMessageEnum;
import com.qsj.acojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.qsj.acojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeSeviceImpl implements JudgeService {
    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManage judgeManage;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        /*
        1. 进行检验，检验题目提交是否存在， 检验题目是否存在
        3，题目的标准输出数据，题目的限制要求
        4. 获得用户提交的代码和语言
        5. 通过判断当前题目提交的状态来确定是否放行，放行后立即修改状态
        5. 调用代码沙箱进行判题，通过配置文件指定使用哪个代码沙箱。
        6. 根据沙箱返回的结果和状态进行判断用户是否回答是否正确，填充返回结果。
           6.0 沙箱的运行状态是否成功
           6.1 先判断用例条数是否与预期的相同
           6.2 依次判断输出用例
           6.3 根据题目限制进行判断


         */
        // 进行检验，检验题目提交是否存在， 检验题目是否存在
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Question question = questionFeignClient.getQuestionById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        //判断该判题任务是否已经在评测
        if (!QuestionSubmitStatusEnum.WAITTING.getValue().equals(questionSubmit.getStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在评测中~");
        }

        // 更改评测状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新失败");
        }

        //获得question: JudgeConfig, 评测用例,输出用例
        List<JudgeCase> listJudgeCase = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> listInputCase = listJudgeCase.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        //获得用户提交的代码和语言
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();

        //调用代码沙箱评测题目
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().code(code).intputList(listInputCase).language(language).build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);

        //依据代码沙箱返回的信息进行判题，并更新题目提交信息。
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(listInputCase);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setStatus(executeCodeResponse.getStatue());
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setJudgeCase(listJudgeCase);

        JudgeInfo finalJudgeInfo = judgeManage.doJudge(judgeContext);

        //设置题目判题情况，设置判题状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setId(questionSubmitId);
        String finalJudgeInfoStr = JSONUtil.toJsonStr(finalJudgeInfo);
        questionSubmitUpdate.setJudgeInfo(finalJudgeInfoStr);
        boolean isUpdate = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if(!isUpdate){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新失败");
        }

        if(JudgeInfoMessageEnum.ACCEPTED.getText().equals(finalJudgeInfo.getMessage())){
            question.setAcceptedNum(question.getAcceptedNum() + 1);
            question.setSubmitNum(question.getSubmitNum() + 1);
            Boolean b = questionFeignClient.updateById(question);
            if(!b){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目通过数更新失败");
            }
        } else {
            question.setSubmitNum(question.getSubmitNum() + 1);
            Boolean b = questionFeignClient.updateById(question);
            if(!b){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目通过数更新失败");
            }
        }

        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;


    }
}
