package com.example.test2.config;

import com.example.test2.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // IoC
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(principalDetailService); // 커스텀 UserDetailsService
        authProvider.setPasswordEncoder(encoderPWD()); // 비밀번호 인코더 설정
        return authProvider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/board/**", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/loginForm")
                        .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해줌
                        .failureHandler(customAuthenticationFailureHandler())
                        .defaultSuccessUrl("/")
                ).logout(logout -> logout
                        .logoutUrl("/logout")  // 로그아웃 요청 URL
                        .logoutSuccessUrl("/")  // 로그아웃 후 메인 페이지로 이동
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")  // JSESSIONID 쿠키 삭제
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("loginError", "로그인에 실패하셨습니다.");
            response.sendRedirect("/auth/user/loginForm");
        };
    }

}
