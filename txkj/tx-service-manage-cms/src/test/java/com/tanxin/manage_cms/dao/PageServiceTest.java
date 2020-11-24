package com.tanxin.manage_cms.dao;


import com.tanxin.manage_cms.service.CmsPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    CmsPageService cmsPageService;


    @Test
    public void PageHtml() throws IOException {
//        String pageHtml = cmsPageService.getPageHtml("5facab23dafc42562436bccb");
        String pageHtml = cmsPageService.getPageHtml("5facab23dafc42562436bccb");
        System.out.println(pageHtml);
    }

}
