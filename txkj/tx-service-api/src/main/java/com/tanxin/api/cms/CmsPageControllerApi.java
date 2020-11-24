package com.tanxin.api.cms;

import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "分页controller",description = "增删改查")
public interface CmsPageControllerApi {
    public CmsPageResult add(CmsPage cmsPage);


    public CmsPage findById(String id);

    public CmsPageResult updateByid(String id,CmsPage cmsPage);


    public ResponseResult delByid(String id);

    @ApiOperation("发布页面")
    public ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    public CmsPageResult save(CmsPage cmsPage);


}
