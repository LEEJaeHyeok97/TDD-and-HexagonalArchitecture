package com.tdd.service;

import com.tdd.domain.Course;
import com.tdd.domain.CourseRegistration;
import com.tdd.domain.User;
import com.tdd.domain.repository.CourseRegistrationRepository;
import com.tdd.domain.repository.CourseRepository;
import com.tdd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;


    public CourseRegistration addRegistration(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다."));

        // 이미 수강신청한 강좌인지 확인
        Optional<CourseRegistration> existingRegistration = registrationRepository.findByUserIdAndCourseId(userId, courseId);
        if (existingRegistration.isPresent()) {
            throw new IllegalArgumentException("이미 수강신청한 강좌입니다.");
        }

        CourseRegistration registration = CourseRegistration.builder()
                .user(user)
                .course(course)
                .build();

        return registrationRepository.save(registration);
    }

    public boolean cancelRegistration(Long userId, Long courseId) {
        Optional<CourseRegistration> registration = registrationRepository.findByUserIdAndCourseId(userId, courseId);

        if (registration.isPresent()) {
            registrationRepository.delete(registration.get());
            return true;
        } else {
            return false;
        }
    }

    public List<CourseRegistration> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUserId(userId);
    }
}
