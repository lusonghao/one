package com.tanxin.manage_course.controller;

import com.tanxin.api.course.CourseControllerApi;
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
import com.tanxin.manage_course.dao.CoursePicRepository;
import com.tanxin.manage_course.service.CateGoryService;
import com.tanxin.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;
    //课程实现类

    @Autowired
    CateGoryService cateGoryService;
    //课程分类实现类

    @Autowired
    CoursePicRepository coursePicRepository;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanList = courseService.findTeachplanList(courseId);
        System.out.println(teachplanList);
        return teachplanList;
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page,@PathVariable("size") int size,CourseListRequest courseListRequest) {

        return courseService.findCourseList(page,size,courseListRequest);
    }

    @Override
    @GetMapping("/category/list")
    public CategoryNode findCategoryNodeList() {
        return cateGoryService.findCategoryNodeList();
    }

    @Override
    @GetMapping("/dictionary/get/{dtype}")
    public SysDictionary findsysdictionary(@PathVariable("dtype") String dtype) {
        return courseService.findsysdictionary(dtype);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult savecourseBase(@RequestBody CourseBase courseBase) {

        return courseService.savecourseBase(courseBase);
    }

    @Override
    @GetMapping("/findcourseByid/{courseId}")
    public CourseBase findcourseByid(@PathVariable("courseId") String courseId) {

        return courseService.findcourseByid(courseId);
    }

    @Override
    @PutMapping("/updateCourseByid/{courseId}")
    public ResponseResult updateCourseByid(@PathVariable("courseId") String courseId, @RequestBody CourseBase courseBase) {

        return courseService.updateCourseByid(courseId,courseBase);
    }

    @Override
    @GetMapping("/getcoursemarket/{id}")
    public CourseMarket getCourseMarketById(@PathVariable("id") String id) {

        return courseService.getCourseMarketById(id);
    }

    @Override
    @PutMapping("/updateCourseMarket/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(id,courseMarket);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {

        return courseService.addCoursePic(courseId,pic);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id")String id) {

        return courseService.getCoruseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }


}
