package com.tdd.service;

import com.tdd.domain.User;
import com.tdd.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원가입_성공_테스트() {
        UserService userService = new UserService(userRepository);

        User user = User.builder()
                .username("student1")
//                .password("password")
                .role("STUDENT")
                .build();

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("student1", savedUser.getUsername());
        assertNotNull(savedUser.getId());
    }

    @Test
    public void 회원가입_실패_테스트_이미존재하는_사용자명() {
        UserService userService = new UserService(userRepository);

        User user1 = User.builder()
                .username("student1")
                .password("password")
                .role("STUDENT")
                .build();

        User user2 = User.builder()
                .username("student1")
                .password("password123")
                .role("STUDENT")
                .build();

        userService.registerUser(user1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user2);
        });

        assertEquals("이미 존재하는 사용자명입니다.", exception.getMessage());
    }

    @Test
    public void 로그인_성공_테스트() {
        UserService userService = new UserService(userRepository);

        User user = User.builder()
                .username("student1")
                .password("password")
                .role("STUDENT")
                .build();

        userService.registerUser(user);

        User loggedInUser = userService.login("student1", "password");

        assertNotNull(loggedInUser);
        assertEquals("student1", loggedInUser.getUsername());
    }

    @Test
    public void 로그인_실패_테스트_잘못된_비밀번호() {
        UserService userService = new UserService(userRepository);

        User user = User.builder()
                .username("student1")
                .password("password")
                .role("STUDENT")
                .build();

        userService.registerUser(user);

        User loggedInUser = userService.login("student1", "wrongpassword");

        assertNull(loggedInUser);
    }

    @Test
    public void 로그인_실패_테스트_존재하지_않는_사용자명() {
        UserService userService = new UserService(userRepository);

        User loggedInUser = userService.login("nonexistent", "password");

        assertNull(loggedInUser);
    }


}