package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsConfigControllerApi;
import com.tanxin.framework.domain.cms.CmsConfig;
import com.tanxin.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {


    @Autowired
    CmsPageService cmsPageService;

    @Override
    @GetMapping("/getmodel/{id}")
    public CmsConfig getmodel(@PathVariable("id") String id) {
        return cmsPageService.getConfigByid(id);
    }
}
