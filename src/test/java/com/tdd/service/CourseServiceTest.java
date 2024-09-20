package com.tdd.service;

import com.tdd.domain.Course;
import com.tdd.domain.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void 강좌_목록_조회_테스트() {
        CourseService courseService = new CourseService(courseRepository);

        courseService.addCourse(Course.builder().title("Math 101").description("기초 수학").build());
        courseService.addCourse(Course.builder().title("Physics 101").description("기초 물리").build());

        List<Course> courses = courseService.getAllCourses();

        assertEquals(2, courses.size());
    }

    @Test
    public void 강좌_상세_정보_조회_테스트() {
        CourseService courseService = new CourseService(courseRepository);

        Course course = courseService.addCourse(Course.builder().title("Chemistry 101").description("기초 화학").build());

        Course foundCourse = courseService.getCourseById(course.getId());

        assertNotNull(foundCourse);
        assertEquals("Chemistry 101", foundCourse.getTitle());
    }

}