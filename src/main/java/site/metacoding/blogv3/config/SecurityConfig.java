package site.metacoding.blogv3.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 스프링의 디폴트 해쉬 알고리즘이 BCrypt이다!!
    public BCryptPasswordEncoder encoder() { // IOC에 등록해서 시큐리티한테 패스워드 암호화 알고리즘을 알려줌.
        return new BCryptPasswordEncoder();
    }

    // 인증 설정하는 메서드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.csrf().disable(); // 이거 안하면 postman 테스트 못함.
        http.authorizeRequests()
                .antMatchers("/s/**").authenticated() // /s/로 시작하는 주소로 갈때만 인증 필요하도록 설정
                .anyRequest().permitAll() // 그 외 주소는 모두 허용
                .and()
                .formLogin() // formLogin 커스터마이징
                // .usernameParameter("user") // username을 user로 키값을 변경
                // .passwordParameter("pwd") // password를 pwd로 키값을 변경.
                .loginPage("/login-form") // 로그인 페이지의 주소를 /loginFrom으로 설정
                .loginProcessingUrl("/login") // login 프로세스를 탐
                // .failureHandler(new AuthenticationFailureHandler() { // 로그인 실패
                // @Override
                // public void onAuthenticationFailure(HttpServletRequest request,
                // HttpServletResponse response,
                // AuthenticationException exception) throws IOException, ServletException {
                // System.out.println("로그인 실패 !!!");
                // response.sendRedirect("/");
                // }
                // })
                // .successHandler(null) // 로그인 성공
                .defaultSuccessUrl("/"); // 로그인 성공시 /로 이동
    }
}
