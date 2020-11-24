package com.tanxin.api.course;


import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CourseMarket;
import com.tanxin.framework.domain.course.CoursePic;
import com.tanxin.framework.domain.course.Teachplan;
import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.ext.CourseView;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import com.tanxin.framework.domain.course.response.CoursePublishResult;
import com.tanxin.framework.domain.system.SysDictionary;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CourseControllerApi {
    public TeachplanNode findTeachplanList(String courseId);

    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("查询我的课程列表")
    public QueryResponseResult findCourseList(
            int page,
            int size,
            CourseListRequest courseListRequest
    );

    public CategoryNode findCategoryNodeList();


    @ApiOperation("查询课程等级")
    public SysDictionary findsysdictionary(String dType);

    @ApiOperation("添加课程")
    public ResponseResult savecourseBase(CourseBase courseBase);

    @ApiOperation("根据id查询课程")
    public CourseBase findcourseByid(String courseId);

    public ResponseResult updateCourseByid(String courseId,CourseBase courseBase);

    public CourseMarket getCourseMarketById(String id);

    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);

    @ApiOperation("添加课程图片")
    public ResponseResult addCoursePic(String courseId,String pic);


    @ApiOperation("获取课程基础信息")
    public CoursePic findCoursePic(String courseId);


    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);

    @ApiOperation("预览课程")
    public CoursePublishResult preview(String id);


}
