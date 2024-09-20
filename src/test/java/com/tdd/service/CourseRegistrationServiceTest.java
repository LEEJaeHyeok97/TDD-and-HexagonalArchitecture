package com.tdd.service;

import com.tdd.domain.Course;
import com.tdd.domain.CourseRegistration;
import com.tdd.domain.User;
import com.tdd.domain.repository.CourseRegistrationRepository;
import com.tdd.domain.repository.CourseRepository;
import com.tdd.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRegistrationServiceTest {
    @Autowired
    private CourseRegistrationRepository registrationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    private CourseRegistrationService courseRegistrationService;
    private UserService userService;
    private CourseService courseService;

    private User user;
    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
        courseService = new CourseService(courseRepository);
        courseRegistrationService = new CourseRegistrationService(registrationRepository, userRepository, courseRepository);

        user = User.builder()
                .username("student1")
                .password("password")
                .role("STUDENT")
                .build();
        userService.registerUser(user);

        course1 = Course.builder()
                .title("Math 101")
                .description("기초 수학")
                .build();
        courseService.addCourse(course1);

        course2 = Course.builder()
                .title("Physics 101")
                .description("기초 물리")
                .build();
        courseService.addCourse(course2);
    }

    @Test
    public void 수강신청_추가_테스트() {
        CourseRegistration registration = courseRegistrationService.addRegistration(user.getId(), course1.getId());

        assertNotNull(registration);
        assertEquals(user.getId(), registration.getUser().getId());
        assertEquals(course1.getId(), registration.getCourse().getId());
    }

    @Test
    public void 수강신청_취소_테스트() {
        courseRegistrationService.addRegistration(user.getId(), course1.getId());
        boolean result = courseRegistrationService.cancelRegistration(user.getId(), course1.getId());

        assertTrue(result);
    }

    @Test
    public void 수강신청_목록_조회_테스트() {
        courseRegistrationService.addRegistration(user.getId(), course1.getId());
        courseRegistrationService.addRegistration(user.getId(), course2.getId());

        List<CourseRegistration> registrations = courseRegistrationService.getRegistrationsByUser(user.getId());

        assertEquals(2, registrations.size());
    }

}