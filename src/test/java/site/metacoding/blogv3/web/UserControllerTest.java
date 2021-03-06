package site.metacoding.blogv3.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;
import site.metacoding.blogv3.util.email.EmailUtil;

// RestController ???????????? ?????????????????? ?????? ?????????. TestRestTemplate ??????
// Controller ???????????? WebMvc??? ?????????(model ??? ?????? ?????? ?????? ?????? ??????.).
// WebMvcTest, SprinbootTest ????????? ????????? ????????? ???????????? ????????? ???????????? ?????? ?????????. 
// SpringbootTest + MockMvc -> ???????????? ?????????
// WebMvcTest + MockMvc -> ???????????? ????????? ???????????? ??????????????? ???.

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
public class UserControllerTest {

    // @Autowired
    // private TestRestTemplate rt;
    @Autowired
    private UserRepository userRepository;

    @MockBean
    private EmailUtil emailUtil;

    // @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context) // main??? run??? ???????????????.
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void joinForm_?????????() throws Exception {
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
    public void loginForm_?????????() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/login-form"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    public void passwordResetForm_?????????() throws Exception {
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
    public void updateForm_?????????() throws Exception {
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

    public void join_?????????() {
        // given

        // when

        // then
        assertEquals("1", "1");
    }

    @Test
    public void passwordReset_?????????() throws Exception {
        // given
        String username = "ssar";
        String email = "wlsdyd1178@naver.com";

        // Mock ????????? ????????? ?????? ??????(EmailUtil)??? stub??? ?????????
        // Mock??? ?????? Mockito ????????? ????????? Springboot Ioc??? Mock??? ????????? ??????. @MockBean ??????!
        doNothing().when(emailUtil).sendEmail("", "", "");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/password-reset")
                        .param("username", username)
                        .param("email", email));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void usernameCheck_?????????() {
        // given

        // when

        // then
        assertEquals("1", "1");
    }

    @WithUserDetails("ssar") // ???????????? ????????? user??? ???????????? ????????? ??? ??????????????? ??????
    @Test
    public void profileImgUpdate_?????????() throws Exception {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();;
        // System.out.println(authentication.getPrincipal()); // user??? ???????????? ??????

        // given
        File file = new File(
                "E:\\green_workspace\\spring_lab\\blogv3\\src\\main\\resources\\static\\images\\dog.jpg");
        MockMultipartFile image = new MockMultipartFile("profileImgFile", "dog.jpg", "image/jpeg",
                Files.readAllBytes(file.toPath()));

        // when
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/s/api/user/profile-img");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        ResultActions resultActions = mockMvc.perform(
                builder.file(image));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
