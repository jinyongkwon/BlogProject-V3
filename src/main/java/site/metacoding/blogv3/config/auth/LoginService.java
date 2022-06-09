package site.metacoding.blogv3.config.auth;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;

@Profile("dev")
@RequiredArgsConstructor
@Service // IOC 컨테이너 등록됨.
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        System.out.println("DB에 확인 들어간다!!!!!");

        Optional<User> userOp = userRepository.findByUsername(username);
        if (userOp.isPresent()) { // 내부적으로 터지기 때문에 throw해도 안탐.
            return new LoginUser(userOp.get());
        }
        return null;
    }

}
