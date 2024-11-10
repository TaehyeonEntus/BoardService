package com.example.boardService.config;

import com.example.boardService.jwt.JWTFilter;
import com.example.boardService.jwt.JWTUtil;
import com.example.boardService.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));    //허용할 주소
                        configuration.setAllowedMethods(Collections.singletonList("*"));                                //허용할 메소드
                        configuration.setAllowCredentials(true);                                                                       //인증정보 허용
                        configuration.setAllowedHeaders(Collections.singletonList("*"));                                 //허용할 헤더
                        configuration.setMaxAge(3600L);                                                                                 //캐싱시간

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));            //헤더 노출

                        return configuration;
                    }
                })));*/
        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 로그인 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 권한 인가
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/join","/boards").permitAll()
                        .requestMatchers("/boards/new").hasRole("USER")
                        .requestMatchers("/boards/{boardId}").permitAll()
                        .requestMatchers("/boards/{boardId}/edit").hasRole("USER")
                        .requestMatchers("/boards/{boardId}/delete").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정 X, JWT 토큰 사용
        http
                .sessionManagement((session)-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
