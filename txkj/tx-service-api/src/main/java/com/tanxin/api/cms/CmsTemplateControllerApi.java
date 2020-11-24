package com.tanxin.api.cms;

import com.tanxin.framework.domain.cms.CmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CmsTemplateControllerApi {
    public List<CmsTemplate> queryTemplateList();
}
