package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {

}
