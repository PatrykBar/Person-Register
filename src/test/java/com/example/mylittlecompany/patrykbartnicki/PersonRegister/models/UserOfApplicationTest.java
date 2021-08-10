package com.example.mylittlecompany.patrykbartnicki.PersonRegister.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserOfApplicationTest {

    @Test
    void shouldThrowIllegalArgumentExceptionForMethodSetEmail() {
        //given
        UserOfApplication userOfApplication = new UserOfApplication();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> userOfApplication.setEmail("eeeemail"));
    }

    @Test
    void shouldSetEmail() {
        //given
        UserOfApplication userOfApplication = new UserOfApplication();

        //when
        userOfApplication.setEmail("eeeemail@gmail.com");

        //then
        assertEquals(userOfApplication.getEmail(), "eeeemail@gmail.com");
    }


}