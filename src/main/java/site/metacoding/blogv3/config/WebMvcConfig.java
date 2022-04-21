package site.metacoding.blogv3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web.xml 설정 파일 -> POJO

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry); // web.xml의 기본설정 유지

        registry
                .addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + uploadFolder) // file 프로토콜은 :/// 사용
                .setCachePeriod(60 * 60) // 초단위 => 한시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
