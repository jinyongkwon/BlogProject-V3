package site.metacoding.blogv3.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;
import site.metacoding.blogv3.domain.visit.Visit;
import site.metacoding.blogv3.domain.visit.VisitRepository;
import site.metacoding.blogv3.handler.ex.CustomApiException;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.util.UtilFileUpload;
import site.metacoding.blogv3.util.email.EmailUtil;
import site.metacoding.blogv3.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Service // IOC 등록
public class UserService {

    // DI
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtil emailUtil;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 프로파일이미지변경(MultipartFile file, Integer userId, HttpSession session) {
        String profileImg = UtilFileUpload.write(uploadFolder, file);
        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            userEntity.setProfileImg(profileImg);
            session.setAttribute("principal", userEntity);
        } else {
            throw new CustomApiException("이미지를 찾을수가 없습니다.");
        }
    }

    @Transactional
    public void 회원가입(User user) {
        // 1. save한번
        String rawPassword = user.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해쉬 알고리즘
        user.setPassword(encPassword);
        User userEntity = userRepository.save(user);

        // 2. save 두번
        Visit visit = new Visit();
        visit.setTotalCount(0L); // Long타입은 L을 넣어줘야한다.
        visit.setUser(userEntity); // 가짜 유저를 넣어서 터트리고 테스트 해보기.
        visitRepository.save(visit);
    }

    public boolean 유저네임중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);
        if (userOp.isPresent()) {
            return false;
        }
        return true;
    }

    @Transactional
    public void 패스워드초기화(PasswordResetReqDto passwordResetReqDto) {
        // 1. username, email 이 같은 것이 있는지 체크 (DB)
        Optional<User> userOp = userRepository.findByUsernameAndEmail(
                passwordResetReqDto.getUsername(),
                passwordResetReqDto.getEmail());

        // 2. 같은 것이 있다면 DB password 초기화 - BCrypt 해시 - update 하기(DB)
        // 원래는 날짜나 데이터를 활용하여 랜덤함수로 비밀번호를 보내주고 메일은 버려버림.
        if (userOp.isPresent()) {
            User userEntity = userOp.get(); // 영속화
            String encPassword = bCryptPasswordEncoder.encode("9999"); // 원래 이렇게 절대 9999적으면 절대안됨!!
            userEntity.setPassword(encPassword);
            // 3. 초기화된 비밀번호 이메일로 전송
            emailUtil.sendEmail(userEntity.getEmail(), "비밀번호 초기화", "초기화된 비밀번호 : 9999");
        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다.");
        }

    } // 더티체킹 (update)
}
