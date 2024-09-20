package com.tdd.domain.repository;

import com.tdd.domain.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByUserId(Long userId);
    Optional<CourseRegistration> findByUserIdAndCourseId(Long userId, Long courseId);
}
