package com.tanxin.manage_course.client;


import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("tx-service-manage-cms")
public interface CmsPageClient {



    @GetMapping("/cms/page/findByid/{id}")
    public CmsPage findById(@PathVariable("id")String id);

    @PostMapping("/cms/page/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);
}
