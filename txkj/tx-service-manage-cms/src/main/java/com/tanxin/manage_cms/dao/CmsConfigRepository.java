package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsConfig;
import com.tanxin.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
