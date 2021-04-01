package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserOfApplication userOfApplication){
        userOfApplication.setPassword(passwordEncoder.encode(userOfApplication.getPassword()));
        userOfApplication.setRole("ROLE_USER");
        userRepository.save(userOfApplication);
    }

}
