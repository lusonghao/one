package com.tanxin.manage_cms.service;

import com.tanxin.api.cms.CmsTemplateControllerApi;
import com.tanxin.framework.domain.cms.CmsTemplate;
import com.tanxin.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsTemplateService implements CmsTemplateControllerApi {
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Override
    public List<CmsTemplate> queryTemplateList() {

        return cmsTemplateRepository.findAll();
    }
}
