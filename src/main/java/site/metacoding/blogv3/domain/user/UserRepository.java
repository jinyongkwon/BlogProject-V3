package site.metacoding.blogv3.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    // 해당 쿼리를 적지 않아도 findByUsername을 적으면 자동으로 쿼리가 작동된다 => 이를 JPA네임쿼리라고 한다
    @Query(value = "SELECT * FROM user WHERE username = :username AND email = :email", nativeQuery = true)
    Optional<User> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

}
