package site.metacoding.blogv3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .loginPage("/loginForm") // 로그인 페이지의 주소를 /loginFrom으로 설정
                .defaultSuccessUrl("/"); // 로그인 성공시 /로 이동
    }
}
