package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@DisplayName("Test cases for user service")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    private UserOfApplication user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
        user = new UserOfApplication();
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
        user.setUsername("papa");
        user.setPassword("thdw");
        //when
        userService.addUser(user);
        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication, equalTo(user));
    }

    @Test
    void userServiceShouldSetDefaultRole() {
        //given
        user.setUsername("papa");
        user.setPassword("thdw");
        //when
        userService.addUser(user);
        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication.getRole(), equalTo(user.getRole()));
    }

    @Test
    void shouldEncodePassword(){
        //given
        user.setUsername("Nanama");
        user.setPassword("papa");

        //when
        userService.addUser(user);

        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication.getPassword(), not(equalTo("papa")));
    }

    @Test
    void shouldCheckUserHaveCorrectUsernameAndPasswordForLogin() {
        //given
        user.setUsername("papa");
        user.setPassword("smerf");

        //when
        userService.addUser(user);

        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertAll(
                () -> assertThat(userOfApplication.getUsername(), equalTo("papa")),
                () -> assertThat(userOfApplication.getPassword(), not(equalTo("smerf")))
        );
    }

    @Test
    void shouldCheckUserHaveNotCorrectUsernameAndPasswordForLogin() {
        //given
        UserOfApplication user = new UserOfApplication();
        user.setUsername("papa");
        user.setPassword("thdw");
        UserOfApplication user2 = new UserOfApplication();
        user2.setUsername("panama");
        user2.setPassword("atakama");

        //when
        userService.addUser(user);

        //then
        ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor = ArgumentCaptor.forClass(UserOfApplication.class);
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication.getUsername(), not(equalTo("panama")));
    }
}