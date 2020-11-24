package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator.
 */
@Transactional
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    long deleteByCourseid(String CourseId);
}
