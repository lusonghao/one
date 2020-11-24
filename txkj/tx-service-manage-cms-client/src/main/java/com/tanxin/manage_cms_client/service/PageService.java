package com.tanxin.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.manage_cms_client.dao.CmsPageRepository;
import com.tanxin.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Optional;

@Service
public class PageService {


    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Resource
    GridFSBucket gridFSBucket;

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;


    public  void savePageToServerPath(String pageId){
        CmsPage cmspageByid = this.findCmspageByid(pageId);
        String htmlFileId = cmspageByid.getHtmlFileId();
        InputStream fileById = this.getFileById(htmlFileId);
        if(fileById==null){
            LOGGER.error("getFileById InputStream is null,htmlFileId:{}",htmlFileId);
            return ;
        }

        String siteId = cmspageByid.getSiteId();
        CmsSite cmsSiteByid = this.findCmsSiteByid(siteId);
        String sitePhysicalPath = cmsSiteByid.getSitePhysicalPath();
        String pagePath = sitePhysicalPath+cmspageByid.getPagePhysicalPath()+cmspageByid.getPageName();
        FileOutputStream fileOutputStream =null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(fileById,fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileById.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }

    public InputStream getFileById(String htmlFileId){
        GridFSFile id = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(id.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(id,gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CmsPage findCmspageByid(String pageId){
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    public CmsSite findCmsSiteByid(String siteId){
        Optional<CmsSite> byId = cmsSiteRepository.findById(siteId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

}
