package com.tanxin.manage_cms.controller;

import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController {

    @Autowired
    CmsSiteService cmsSiteService;

    @GetMapping("/list")
    public List<CmsSite> siteList(){
        return cmsSiteService.findSiteList();
    }
}
