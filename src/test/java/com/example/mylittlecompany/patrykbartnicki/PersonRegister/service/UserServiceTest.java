package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void shouldFindAll() {
        //when
        userService.getAllUsers();
        //then
        verify(userRepository).findAll();
    }

    @Test
    void shouldAddUser() {
        //given
        UserOfApplication user = new UserOfApplication();
        user.setId(Long.valueOf(1));
        user.setUsername("papa");
        user.setPassword("thdw");
        //when
        userService.addUser(user);
        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication).isEqualTo(user);
        assertThat(userOfApplication.getRole()).isEqualTo(user.getRole());
    }

    @Test
    void shouldCheckUserForLogin() {
        //given
        UserOfApplication user = new UserOfApplication();
        user.setId(Long.valueOf(1));
        user.setUsername("papa");
        user.setPassword("thdw");

        UserOfApplication user2 = new UserOfApplication();
        user2.setId(Long.valueOf(2));
        user2.setUsername("panama");
        user2.setPassword("atakama");
        //when
        userService.addUser(user);
        //then
        assertThat(userService.checkUserForLogin(user)).isTrue();
    }
}