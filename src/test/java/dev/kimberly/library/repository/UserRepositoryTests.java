package dev.kimberly.library.repository;

import dev.kimberly.library.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataMongoTest
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUpDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {

        // Arrange
        User user = User.builder()
                .firstName("Jane")
                .surname("Doe").build();

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotBlank();
    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThanOneUser() {

        // Arrange
        User user = User.builder()
                .firstName("Jane")
                .surname("Doe").build();

        User user2 = User.builder()
                .firstName("Michael")
                .surname("Roberts").build();

        userRepository.save(user);
        userRepository.save(user2);

        // Act
        List<User> userList = userRepository.findAll();

        // Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

}
