package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator.
 */
@Transactional
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
