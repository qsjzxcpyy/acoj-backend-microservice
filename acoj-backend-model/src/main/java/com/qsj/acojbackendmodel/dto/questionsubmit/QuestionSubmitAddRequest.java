package com.qsj.acojbackendmodel.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liqsj">程序员鱼皮</a>
 * @from <a href="https://qsj.icu">编程导航知识星球</a>
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交的代码
     */
    private String code;

    private static final long serialVersionUID = 1L;
}