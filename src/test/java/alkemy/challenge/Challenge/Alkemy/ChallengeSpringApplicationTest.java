package alkemy.challenge.Challenge.Alkemy;

import alkemy.challenge.Challenge.Alkemy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
class ChallengeSpringApplicationTest {

    @Autowired()
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println("test");
    }

}
