package site.metacoding.blogv3;


import lombok.Data;
import org.junit.jupiter.api.Test;

@Data
class Person {
    private String username;
    private String password;

    public Person username(String username) {
        this.username = username;
        return this;
    }

    public Person password(String password) {
        this.password = password;
        return this;
    }

    public static Person builder() {
        return new Person();
    }
}

public class builderTest {

    @Test
    public void 빌더_테스트() {
        Person person = Person.builder().username("ssar").password("1234");
        System.out.println(person);
    }
}
