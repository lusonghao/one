package com.tanxin.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.tanxin.api.cms.CmsPageControllerApi;
import com.tanxin.framework.domain.cms.CmsConfig;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.CmsTemplate;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.domain.cms.response.CmsCode;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_cms.config.RabbitmqConfig;
import com.tanxin.manage_cms.dao.CmsConfigRepository;
import com.tanxin.manage_cms.dao.CmsPageRepository;
import com.tanxin.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CmsPageService{

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

   @Resource
   GridFSBucket gridFSBucket;


   @Autowired
   RabbitTemplate rabbitTemplate;




    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){

        if(queryPageRequest==null){
            queryPageRequest = new QueryPageRequest();
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }

        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        if(page<=0){
            page=1;
        }
        page=page-1;
        if(size<=0){
            size=10;
        }
        Pageable pageable = PageRequest.of(page,size);
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

/*
    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        CmsPage cmsp = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsp==null){
            CmsPage save = cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }
*/


    public CmsPageResult add(CmsPage cmsPage) {
        if(cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        CmsPage cmsp = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsp!=null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,save);
    }


    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
            return null;

    }

    public CmsPageResult updateByid(String id, CmsPage cmsPage) {
        CmsPage one = this.findById(id);
        if(Objects.nonNull(one)){
            one.setTemplateId(cmsPage.getTemplateId());
//更新所属站点
            one.setSiteId(cmsPage.getSiteId());
//更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
//更新页面名称
            one.setPageName(cmsPage.getPageName());
//更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());

            one.setDataUrl(cmsPage.getDataUrl());
//更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            CmsPage save = cmsPageRepository.save(one);
            if(save!=null){
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }

        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public ResponseResult delByid(String id) {
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if(byId.isPresent()){
            cmsPageRepository.deleteById(id);
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }



    public CmsConfig getConfigByid(String id){
        Optional<CmsConfig> byId = cmsConfigRepository.findById(id);
        if(byId.isPresent()){
            CmsConfig cmsConfig = byId.get();
            return cmsConfig;
        }
        return null;
    }



    public String getPageHtml(String pageId){
        Map model = getModelByPageId(pageId);
        if(model==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        String gettembuid = null;
        try {
            gettembuid = getTemplateByPageId(pageId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(gettembuid)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String s = generateHtml(gettembuid, model);
        return s;

    }


    private String generateHtml(String templateContent,Map model){
        Configuration configuration=new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template = configuration.getTemplate("template");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    private String getTemplateByPageId(String pageId) throws IOException {
        CmsPage byId = this.findById(pageId);
        if(byId==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXSIST);
        }
        String templateId = byId.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

//        Optional<CmsTemplate> byId1 = cmsTemplateRepository.findById(pageId);
        Optional<CmsTemplate> byId1 = cmsTemplateRepository.findById(templateId);
        if(byId1.isPresent()){
            CmsTemplate cmsTemplate = byId1.get();
            String templateFileId = cmsTemplate.getTemplateFileId();
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
            return s;
        }
        return null;


    }

    private Map getModelByPageId (String pageId){
        CmsPage byId = this.findById(pageId);
        if(byId==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXSIST);
        }
        String dataUrl = byId.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }

    public ResponseResult post(String pageId){

        String pageHtml = this.getPageHtml(pageId);

        CmsPage cmsPage = saveHtml(pageId, pageHtml);

        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CmsPageResult save(CmsPage cmsPage) {
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1!=null){
            CmsPageResult cmsPageResult = this.updateByid(cmsPage1.getPageId(), cmsPage);
            return cmsPageResult;
        }else{
            return this.add(cmsPage);
        }
    }


    private void sendPostPage(String pageId){

        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Map<String,String> msg = new HashMap<>();

        msg.put("pageId",pageId);

        String s = JSON.toJSONString(msg);
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,s);
    }


    private CmsPage saveHtml(String pageId,String htmlContent){
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId store =null;
        try {
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            store = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cmsPage.setHtmlFileId(store.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;

    }


}
