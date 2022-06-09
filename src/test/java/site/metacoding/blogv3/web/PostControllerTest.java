package site.metacoding.blogv3.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blogv3.config.auth.LoginUser;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.category.CategoryRepository;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.web.dto.post.PostWriteReqDto;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context) // main의 run을 가져오는것.
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public void love_테스트() {
    }

    public void unLove_테스트() {
    }

    public void delete_테스트() {
    }

    public void detail_테스트() {
    }

    @WithUserDetails("ssar")
    @Test
    public void write_테스트() throws Exception {
        // given
        // 세션에 principal담기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // principal에다가 스프링 카테고리 생성
        // Category category = Category.builder().title("스프링").user(principal).build();
        // Category categoryEntity = categoryRepository.save(category);
        // DB에는 들어가있지만 commit은 안된상태라서 id를 지정해주지 않으면 id를 찾지못함.
        // => DBInitializer를 사용해서 test를 할때 DB에 자동으로 Save를 해주는것으로 해결

        // postWriteReqDto작성
        PostWriteReqDto postWriteReqDto = PostWriteReqDto.builder()
                .categoryId(1) // id를 지정해주지 않을경우 commit이 안되서 아직안보임 => 즉 유령데이터.
                .title("스프링1강")
                .content("재밌음")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/s/post")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", postWriteReqDto.getTitle())
                        .param("content", postWriteReqDto.getContent())
                        .param("categoryId", postWriteReqDto.getCategoryId().toString()));

        // then
        resultActions
                .andExpect(redirectedUrl("/user/" + loginUser.getUser().getId() + "/post"))
                .andDo(MockMvcResultHandlers.print());
    }

    public void writeForm_테스트() {
    }

    public void postList_테스트() {
    }
}

@Service
@Profile("test")
class TestUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = User.builder()
                .id(1)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .profileImg(null)
                .build();
        return new LoginUser(principal);
    }

}
