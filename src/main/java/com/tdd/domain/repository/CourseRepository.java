package com.tdd.domain.repository;

import com.tdd.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CourseRepository extends JpaRepository<Course, Long> {

}
