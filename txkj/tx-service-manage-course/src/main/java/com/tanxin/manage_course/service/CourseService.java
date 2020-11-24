package com.tanxin.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CourseMarket;
import com.tanxin.framework.domain.course.CoursePic;
import com.tanxin.framework.domain.course.Teachplan;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.ext.CourseView;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import com.tanxin.framework.domain.course.response.CourseCode;
import com.tanxin.framework.domain.course.response.CoursePublishResult;
import com.tanxin.framework.domain.system.SysDictionary;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_course.client.CmsPageClient;
import com.tanxin.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    @Resource
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachPlanRepository teachPlanRepository;

    @Resource
    CourseMapper courseMapper;

    @Autowired
    SysDictionaryRepository sysDictionaryRepository;


    @Autowired
    CourseBaseRepository courseBaseRepository;


    @Autowired
    CourseMarketRepository courseMarketRepository;

    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CmsPageClient cmsPageClient;

    @Value("${course‐publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course‐publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course‐publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course‐publish.siteId}")
    private String publish_siteId;
    @Value("${course‐publish.templateId}")
    private String publish_templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;




    public TeachplanNode  findTeachplanList(String courseId){
        return  teachplanMapper.selectList(courseId);
    }


    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        if(teachplan==null || StringUtils.isEmpty(teachplan.getCourseid())
            || StringUtils.isEmpty(teachplan.getPname())
        ){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        String courseid = teachplan.getCourseid();

        String parentid = teachplan.getParentid();

        if(StringUtils.isEmpty(parentid)){
            parentid = getTeachplanRoot(courseid);
        }

        Optional<Teachplan> byId = teachPlanRepository.findById(parentid);
        Teachplan teachplan1 = byId.get();
        String grade = teachplan1.getGrade();

        Teachplan teachplanNew = new Teachplan();

        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        if(grade.equals("1")){
            teachplanNew.setGrade("2");
        }else{
            teachplanNew.setGrade("3");
        }

        teachPlanRepository.save(teachplanNew);

        return new ResponseResult(CommonCode.SUCCESS);


    }

    private String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();

        List<Teachplan> teachplanlist = teachPlanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanlist==null || teachplanlist.size()<=0){
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setStatus("0");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseId);
            teachplan.setGrade("1");
            //添加并返回id
            return teachPlanRepository.save(teachplan).getId();
        }
        return teachplanlist.get(0).getId();

    }

    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseList = courseMapper.findCourseList();
//        List<CourseInfo> result = courseList.getResult();
//        long total = courseList.getTotal();
        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(courseList.getResult());
        queryResult.setTotal(courseList.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);

    }

    public SysDictionary findsysdictionary(String dtype) {
        SysDictionary byDType = sysDictionaryRepository.findByDType(dtype);
        System.out.println(byDType);
        return byDType;
    }

    public ResponseResult savecourseBase(CourseBase courseBase) {
        if(Objects.isNull(courseBase)){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        CourseBase save = courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //回显
    public CourseBase findcourseByid(String courseId) {
        Optional<CourseBase> byId = courseBaseRepository.findById(courseId);
        if(byId.isPresent()){
            CourseBase courseBase = byId.get();
            return courseBase;
        }
        return null;
    }

    //修改
    public ResponseResult updateCourseByid(String courseId, CourseBase courseBase) {
        courseBase.setId(courseId);
        CourseBase save = courseBaseRepository.save(courseBase);
        if(save==null){
            return new ResponseResult(CommonCode.FAIL);
        }else{
            return new ResponseResult(CommonCode.SUCCESS);
        }

    }

    public CourseMarket getCourseMarketById(String id) {
        Optional<CourseMarket> byId = courseMarketRepository.findById(id);
        if(byId.isPresent()){
            CourseMarket courseMarket = byId.get();
            return courseMarket;
        }
        return null;
    }


    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        courseMarket.setId(id);
        CourseMarket save = courseMarketRepository.save(courseMarket);
        if(save==null){
            return new ResponseResult(CommonCode.FAIL);
        }else{
            return new ResponseResult(CommonCode.SUCCESS);

        }
    }


    public ResponseResult addCoursePic(String courseId, String pic) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        CoursePic coursePic=null;
        if(byId.isPresent()){
            coursePic=byId.get();
        }
        if(coursePic==null){
            coursePic=new CoursePic();
        }
        coursePic.setPic(pic);
        coursePic.setCourseid(courseId);
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursePic(String courseId) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        if(byId.isPresent()){
            CoursePic coursePic = byId.get();
            return coursePic;
        }
        return null;
    }

    public ResponseResult deleteCoursePic(String courseId) {
        long l = coursePicRepository.deleteByCourseid(courseId);
        if(l>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    public CourseView getCoruseView(String id) {
        CourseView courseView = new CourseView();
        Optional<CourseBase> courseBase = courseBaseRepository.findById(id);
        if(courseBase.isPresent()){
            courseView.setCourseBase(courseBase.get());
        }
        Optional<CoursePic> coursePic = coursePicRepository.findById(id);
        if(coursePic.isPresent()){
            courseView.setCoursePic(coursePic.get());
        }

        Optional<CourseMarket> courseMarket = courseMarketRepository.findById(id);
        if(courseMarket.isPresent()){
            courseView.setCourseMarket(courseMarket.get());
        }

        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);

        return courseView;


    }

    //课程预览
    public CoursePublishResult preview(String id) {
        CourseBase courseBaseById = this.findCourseBaseById(id);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setTemplateId(publish_templateId);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        cmsPage.setPageName(id+".html");
        cmsPage.setPageAliase(courseBaseById.getName());

        CmsPageResult save = cmsPageClient.save(cmsPage);
        if(!save.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }



        CmsPage cmsPage1 = save.getCmsPage();
        String pageId = cmsPage1.getPageId();

        String url = previewUrl+pageId;
        return new CoursePublishResult(CommonCode.SUCCESS,url);


    }


    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()){

            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;
    }
}
