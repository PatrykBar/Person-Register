package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@DisplayName("Test cases for user service")
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<UserOfApplication> userOfApplicationArgumentCaptor;

    private UserOfApplication user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
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
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userOfApplication.getPassword(), not(equalTo("papa")));
    }

    @Test
    void shouldCheckUserHaveCorrectUsernameForLogin() {
        //given
        user.setUsername("papa");
        user.setPassword("smerf");

        //when
        userService.addUser(user);

        //then
        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
        assertThat(userService.checkUserForLogin(userOfApplication), equalTo(true));
    }

//    @Test
//    void shouldCheckUserHaveNotCorrectUsernameForLogin() {
//        //given
//        UserOfApplication user = new UserOfApplication();
//        user.setUsername("papa");
//        user.setPassword("thdw");
//        UserOfApplication user2 = new UserOfApplication();
//        user2.setUsername("panama");
//        user2.setPassword("atakama");
//        userService.addUser(user);
//
//        //when
//        userService.checkUserForLogin(user2);
//
//        //then
//        ArgumentCaptor<String> userName = ArgumentCaptor.forClass(String.class);
//
//        verify(userRepository).save(userOfApplicationArgumentCaptor.capture());
//        verify(userRepository).findByUsername(userName.capture());
//
//        UserOfApplication userOfApplication = userOfApplicationArgumentCaptor.getValue();
//        String userOfApplicationName = userName.getValue();
//
//        assertAll(
//                () -> assertThat(userOfApplicationName, equalTo(user2.getUsername())),
//                () -> assertThat(user.getUsername(), not(equalTo(userOfApplicationName))),
//                () -> assertThat(userOfApplication.getUsername(), not(equalTo(user2.getUsername())))
//        );
//    }
}