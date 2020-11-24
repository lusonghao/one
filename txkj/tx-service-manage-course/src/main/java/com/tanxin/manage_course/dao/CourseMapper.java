package com.tanxin.manage_course.dao;

import com.github.pagehelper.Page;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator.
 */
@Mapper
@Transactional
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);

   Page<CourseInfo> findCourseList();

}
