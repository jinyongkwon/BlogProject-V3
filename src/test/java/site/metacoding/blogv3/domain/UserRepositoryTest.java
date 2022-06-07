package site.metacoding.blogv3.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.web.WebAppConfiguration;

import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;

@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void save_테스트() {
        // given
        String username = "cos";
        String password = "1234"; // 해시로 변경하는 것이 이친구의 책임이 아니다, 만약 해시를 안할경우 오류가 터질것이라 예상되면 주석으로 적어놓자.
        String email = "cos@nate.com";
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        User user = new User(null, username, password, email, null, createDate, updateDate);

        // when
        User uesrEntity = userRepository.save(user);

        // then
        assertEquals(username, uesrEntity.getUsername());
    }

    @Test
    @Order(2)
    public void findByUsername_테스트() {
        // given
        String username = "cos";
        String password = "1234";
        String email = "cos@nate.com";
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        User user = new User(null, username, password, email, null, createDate, updateDate);
        userRepository.save(user);

        // when
        User userEntity = userRepository.findByUsername(username).get();

        // then
        assertEquals(username, userEntity.getUsername());
    }

    @Test
    @Order(3)
    public void findByUsernameAndEmail_테스트() {
        // given
        String username = "cos";
        String password = "1234";
        String email = "cos@nate.com";
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        User user = new User(null, username, password, email, null, createDate, updateDate);
        userRepository.save(user);

        // when
        User userEntity = userRepository.findByUsernameAndEmail(username, email).get();

        // then
        assertEquals(username, userEntity.getUsername());

    }

    @Test
    @Order(4)
    public void findById_테스트() {
        // given
        String username = "cos";
        String password = "1234";
        String email = "cos@nate.com";
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        User user = new User(null, username, password, email, null, createDate, updateDate);
        userRepository.save(user);
        Integer id = 1;

        // when
        User userEntity = userRepository.findById(id).get();

        // then
        assertEquals(username, userEntity.getUsername());
    }

}
