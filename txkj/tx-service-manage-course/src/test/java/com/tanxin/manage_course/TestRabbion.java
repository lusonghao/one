package com.tanxin.manage_course;

import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.manage_course.client.CmsPageClient;
import com.tanxin.manage_course.dao.CourseBaseRepository;
import com.tanxin.manage_course.dao.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRabbion {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsPageClient cmsPageClient;


    @Test
    public void testRabbion(){
        CmsPage byId = cmsPageClient.findById("5facab23dafc42562436bccb");
        System.out.println(byId);


    }





}
