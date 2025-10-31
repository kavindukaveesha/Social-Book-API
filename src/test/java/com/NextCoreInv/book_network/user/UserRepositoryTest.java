package com.NextCoreInv.book_network.user;

import com.NextCoreInv.book_network.role.TestJpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@Sql(scripts = {"/test-data.sql"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setEmail("test@test.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@test.com");

        assertTrue(foundUser.isPresent());
    }
}
