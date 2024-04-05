package com.qsj.acojbackendjudgeservice.judge.codesandbox;


import com.qsj.acojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.qsj.acojbackendmodel.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 运行代码，返回结果
     * @param executeCodeRequest
     * @return
     */
     ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
