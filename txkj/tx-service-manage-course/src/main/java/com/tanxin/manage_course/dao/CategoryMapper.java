package com.tanxin.manage_course.dao;

import com.github.pagehelper.Page;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CategoryMapper {
   CategoryNode findCategoryNodeList();
}
