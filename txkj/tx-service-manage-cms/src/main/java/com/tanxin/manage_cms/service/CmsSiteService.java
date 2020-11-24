package com.tanxin.manage_cms.service;

import com.tanxin.api.cms.CmsSiteControllerApi;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsSiteService implements CmsSiteControllerApi {

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Override
    public List<CmsSite> findSiteList() {
        return cmsSiteRepository.findAll();
    }
}
