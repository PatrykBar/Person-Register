package com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@DisplayName("Test cases for user repository")
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserOfApplication user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserOfApplication();
    }

    @Test
    void shouldFindByUsername() {
        //given
        user.setUsername("papa");
        user.setPassword("115ffff");
        userRepository.save(user);

        //when
        Optional<UserOfApplication> userExist = userRepository.findByUsername("papa");

        //then
        verify(userRepository).findByUsername("papa");
    }
}