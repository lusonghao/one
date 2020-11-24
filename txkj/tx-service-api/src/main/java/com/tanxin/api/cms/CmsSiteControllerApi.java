package com.tanxin.api.cms;

import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.model.response.QueryResponseResult;

import java.util.List;

public interface CmsSiteControllerApi {
    public List<CmsSite> findSiteList();
}
