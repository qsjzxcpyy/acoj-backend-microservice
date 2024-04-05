package com.qsj.acojbackendjudgeservice.judge.codesandbox;

import com.qsj.acojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandboxImpl;
import com.qsj.acojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandboxImpl;
import com.qsj.acojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandboxImpl;

public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type){
        switch(type){
            case "example":
                return new ExampleCodeSandboxImpl();
            case "remote":
                return new RemoteCodeSandboxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandboxImpl();
            default:
                return new ExampleCodeSandboxImpl();
        }
    }
}
