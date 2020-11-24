package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindPage(){
        int page=0;
        int size=10;

        Pageable pageable = PageRequest.of(page,size);

        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);

    }


    @Test
    public void testSave(){

        Optional<CmsPage> byId = cmsPageRepository.findById("5a754adf6abb500ad05688d9");
        if(byId.isPresent()){
            CmsPage cmsPage = byId.get();
            cmsPage.setPageAliase("首页");
            cmsPageRepository.save(cmsPage);
        }

    }

    @Test
    public void test1(){
        CmsPage byPageName = cmsPageRepository.findByPageName("index.html");
        System.out.println(byPageName);

    }

    @Test
    public void TestFind(){
        int page=0;
        int size=10;
        Pageable pageable = PageRequest.of(page,size);
        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageAliase("");
        ExampleMatcher matching = ExampleMatcher.matching();
        ExampleMatcher exampleMatcher = matching.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.println(all);

    }

}
