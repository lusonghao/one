package com.tanxin.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.tanxin.filesystem.dao.FileSystemRepository;
import com.tanxin.framework.domain.filesystem.FileSystem;
import com.tanxin.framework.domain.filesystem.response.FileSystemCode;
import com.tanxin.framework.domain.filesystem.response.UploadFileResult;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemService {

    @Value("${tanxin.fastdfs.tracker_servers}")
    String tracker_servers;

    @Value("${tanxin.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;

    @Value("${tanxin.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;

    @Value("${tanxin.fastdfs.charset}")
    String charset;


    @Autowired
    FileSystemRepository fileSystemRepository;








    public UploadFileResult upload(MultipartFile multipartFile, String filetag,
                                   String businesskey,
                                   String metadata){
        //将文件上传到dfs中，得到一个文件的id
        if(multipartFile==null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        String fileid = fdfs_upload(multipartFile);
        if(StringUtils.isEmpty(fileid)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }


        FileSystem fileSystem = new FileSystem();
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileId(fileid);
        fileSystem.setFilePath(fileid);
        fileSystem.setFiletag(filetag);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        if(StringUtils.isNotEmpty(metadata)){
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);




    }



    //上传文件到dfs中
    private String fdfs_upload(MultipartFile multipartFile){
        initFastConfig();//初始化dfs环境
        TrackerClient trackerClient = new TrackerClient();//创建
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //得到文件字节
            byte[] bytes = multipartFile.getBytes();
            //得到文件原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileid = storageClient1.upload_appender_file1(bytes, substring, null);
            return fileid;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }



    //初始化dfs环境
    private void initFastConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);



        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }
}
