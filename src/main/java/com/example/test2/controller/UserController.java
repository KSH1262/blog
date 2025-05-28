package com.example.test2.controller;

import com.example.test2.config.auth.PrincipalDetail;
import com.example.test2.dto.UserJoinDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/user/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "/user/joinForm";
    }

    @GetMapping("/auth/user/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("user/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
        model.addAttribute("principal", principal);
        return "user/updateForm";
    }
}