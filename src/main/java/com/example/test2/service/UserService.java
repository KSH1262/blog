package com.example.test2.service;

import com.example.test2.entity.RoleType;
import com.example.test2.entity.User;
import com.example.test2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(User user) {
        String rawPassword = user.getPassword(); // 비밀번호 원문
        String encPassword = encoder.encode(rawPassword); // hash
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void edit(User user) {
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setEmail(user.getEmail());
    }
}
