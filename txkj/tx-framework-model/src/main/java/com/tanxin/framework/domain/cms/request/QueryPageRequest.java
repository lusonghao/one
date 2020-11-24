package com.tanxin.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRequest {
    //接受页面查询的条件

    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //页面id；
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模型id
    private String templateId;
}
