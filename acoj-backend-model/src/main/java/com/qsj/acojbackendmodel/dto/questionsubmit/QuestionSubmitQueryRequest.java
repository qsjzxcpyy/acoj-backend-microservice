package com.qsj.acojbackendmodel.dto.questionsubmit;

import com.qsj.acojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liqsj">程序员鱼皮</a>
 * @from <a href="https://qsj.icu">编程导航知识星球</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest  extends PageRequest implements Serializable {


    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 编程语言
     */
    private String language;


    /**
     * 题目测评状态，0 未测评 1 正在测评 2 测评成功 3 测评失败
     */
    private Integer status;



    private static final long serialVersionUID = 1L;
}