package com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindByUsername() {
        //given
        UserOfApplication user = new UserOfApplication();
        user.setId(Long.valueOf(1));
        user.setUsername("papa");
        user.setPassword("115ffff");
        userRepository.save(user);

        //when
        Optional<UserOfApplication> userExist = userRepository.findByUsername("papa");

        //then
        assertThat(userExist.get().getUsername()).isEqualTo("papa");

    }
}