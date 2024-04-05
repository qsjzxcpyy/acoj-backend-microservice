package com.qsj.acojbackendquestionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsj.acojbackendmodel.entity.QuestionSubmit;
import org.mapstruct.Mapper;


/**
* @author qsj
* @description 针对表【question_submit(判题任务表)】的数据库操作Mapper
* @createDate 2024-03-09 16:21:50
* @Entity com.qsj.acoj.model.entity.QuestionSubmit
*/
@Mapper
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

}




