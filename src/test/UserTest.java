package test;


import org.junit.jupiter.api.Test;
import server.User;
import org.assertj.core.api.Assertions;


public class UserTest {
    User user = new User();
    @Test
    void 로그인_테스트(){
        Assertions.assertThat(user.isLogin("Joo", "1234")).isTrue();
    }

    @Test
    void 로그인_실패_테스트() {
        Assertions.assertThat(user.isLogin("Joo", "5678")).isFalse();
    }

}
