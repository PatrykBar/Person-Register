package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserOfApplication> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean addUser(UserOfApplication userOfApplication){
        Pattern pattern = Pattern.compile("^[\\w.,!?;:@#$%&]+$");
        Matcher matcher = pattern.matcher(userOfApplication.getUsername());
        Boolean userNameChecker = matcher.matches();

        if (userRepository.findByUsername(userOfApplication.getUsername()).isPresent() && userNameChecker){
            return false;
        }else{
            userOfApplication.setPassword(passwordEncoder.encode(userOfApplication.getPassword()));
            userOfApplication.setRole("ROLE_USER");
            userRepository.save(userOfApplication);
            return true;
        }
    }

    public boolean checkUserForLogin(UserOfApplication userOfApplication){
        if (userRepository.findByUsername(userOfApplication.getUsername()).isPresent()){
            return false;
        }else{
            return true;
        }
    }

}