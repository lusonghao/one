package com.tanxin.api.filesystem;

import com.tanxin.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(value="文件管理接口",description = "文件管理接口，提供数据模型的管理、查询接口")
public interface FileSystemControllerApi {

    @ApiOperation("上传文件")
    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata
    );
}
