package com.tanxin.manage_cms_client.dao;

import com.tanxin.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {

}
