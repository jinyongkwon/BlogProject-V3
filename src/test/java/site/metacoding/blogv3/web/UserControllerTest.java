package site.metacoding.blogv3.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blogv3.domain.user.User;

// RestController 테스트는 통합테스트로 하면 편하다. TestRestTemplate 사용
// Controller 테스트는 WebMvc가 필요함(model 값 검증 같은 것을 할수 있다.).
// WebMvcTest, SprinbootTest 둘중에 무엇을 쓸지는 메모리에 무엇을 올릴지에 따라 다르다. 
// SpringbootTest + MockMvc -> 메모리에 다올림
// WebMvcTest + MockMvc -> 컨트롤러 앞단만 메모리에 올리겠다는 것.

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
public class UserControllerTest {

    // @Autowired
    // private TestRestTemplate rt;

    // @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context) // main의 run을 가져오는것.
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void joinForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/join-form"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

        // // when
        // ResponseEntity<String> responseEntity = rt.exchange("/join-form",
        // HttpMethod.GET, null, String.class);
        // System.out.println("=======================================");
        // System.out.println(responseEntity.getStatusCodeValue());
        // System.out.println(responseEntity.getHeaders().getContentType());
        // System.out.println("=======================================");

        // // then
        // assertEquals(200, responseEntity.getStatusCodeValue());
        // assertEquals("html",
        // responseEntity.getHeaders().getContentType().getSubtype());

    }

    @Test
    public void loginForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/login-form"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    public void passwordResetForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/password-reset-form"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @WithMockUser
    @Test
    public void updateForm_테스트() throws Exception {
        // given
        Integer id = 1;
        User principal = User.builder()
                .id(1)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .profileImg(null)
                .build();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("principal", principal);

        // when
        ResultActions resultActions = mockMvc.perform(get("/s/user/" + id).session(session));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    public void join_테스트() {
    }

    public void passwordReset_테스트() {
    }

    @Test
    public void usernameCheck_테스트() {
        // given

        // when

        // then
    }

    public void profileImgUpdate_테스트() {

    }
}
