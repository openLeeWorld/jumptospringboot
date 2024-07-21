package com.mysite.sbb;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@Configuration // 스프링의 환경 설정 파일임의 의미하는 애너테이션
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 함
@EnableMethodSecurity(prePostEnabled=true) // @PreAuthorize 사용을 위해 필요
public class SecurityConfig { // 내부적으로 필터 체인이 모든 요청 URL에 적용됨
	@Bean // 스프링 빈(스프링에 의해 생성, 관리되는 객체)을 생성해서 주입
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		.csrf((csrf) -> csrf
				.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
		.headers((headers) -> headers
			.addHeaderWriter(new XFrameOptionsHeaderWriter(
					XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		.formLogin((formLogin) -> formLogin
				.loginPage("/user/login")
				.defaultSuccessUrl("/"))
		.logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)) // 로그아웃 시 생성한 사용자 세션 삭제
		;
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager anthenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception { 
		// Manager는 스프링 시큐리티의 인증을 처리함
		// UserSecurityService와 PasswordEncoder를 내부적으로 사용하여
		// 인증과 권한 부여 프로세스를 처리함
		return authenticationConfiguration.getAuthenticationManager();
	}
}
