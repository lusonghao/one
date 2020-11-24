package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsPageControllerApi;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController  implements CmsPageControllerApi {

    @Autowired
    CmsPageService cmsPageService;

    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size")int size, QueryPageRequest queryPageRequest) {
        /*QueryResult queryResult = new QueryResult();
        List<CmsPage> list = new ArrayList<>();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        queryResult.setTotal(1);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;*/
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage){
        return cmsPageService.add(cmsPage);
    }


    @GetMapping("/findByid/{id}")
    public CmsPage findById(@PathVariable("id")String id){
        return cmsPageService.findById(id);
    }

    @PutMapping("/updateByid/{id}")
    public CmsPageResult updateByid(@PathVariable("id")String id,@RequestBody CmsPage cmsPage){
        return cmsPageService.updateByid(id,cmsPage);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delByid(@PathVariable("id") String id){
        return cmsPageService.delByid(id);
    }


    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId){
        return cmsPageService.post(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }


}
