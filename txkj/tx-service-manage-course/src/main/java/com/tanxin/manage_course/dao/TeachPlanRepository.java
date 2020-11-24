package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator.
 */
@Transactional
public interface TeachPlanRepository extends JpaRepository<Teachplan,String> {
    List<Teachplan> findByCourseidAndParentid(String couseId,String parentid);
}
