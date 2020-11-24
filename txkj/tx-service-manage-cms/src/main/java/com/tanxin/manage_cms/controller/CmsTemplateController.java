package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsTemplateControllerApi;
import com.tanxin.framework.domain.cms.CmsTemplate;
import com.tanxin.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController {

    @Autowired
    CmsTemplateService  cmsTemplateService;

    @GetMapping("/list")
    public List<CmsTemplate> list(){
        return cmsTemplateService.queryTemplateList();
    }
}
