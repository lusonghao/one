package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator.
 */
@Mapper
@Transactional
public interface TeachplanMapper {
   TeachplanNode selectList(String courseId);
}




