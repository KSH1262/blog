package com.example.test2.controller.api;

import com.example.test2.dto.ResponseDto;
import com.example.test2.dto.UserJoinDto;
import com.example.test2.entity.RoleType;
import com.example.test2.entity.User;
import com.example.test2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/join")
    public ResponseEntity<?> save(@Valid @RequestBody UserJoinDto userJoinDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 에러 메시지 수집
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMap);
        }

        // 가입 처리
        User user = User.builder()
                .username(userJoinDto.getUsername())
                .password(userJoinDto.getPassword())
                .email(userJoinDto.getEmail())
                .role(RoleType.USER)
                .build();

        userService.join(user);
        return ResponseEntity.ok().body(Collections.singletonMap("message", "회원가입 성공"));
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.edit(user);
        // 여기서 트랜잭션이 종료되기 때문에 DB값은 변경됨
        // 하지만 세션값은 변경되지 않은 상태, 세션값 변경해줄거임
        // 세션 등록
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
