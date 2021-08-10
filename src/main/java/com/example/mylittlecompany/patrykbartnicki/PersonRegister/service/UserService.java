package com.example.mylittlecompany.patrykbartnicki.PersonRegister.service;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.repositories.UserRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository userRepository;
    private Argon2PasswordEncoder encoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new Argon2PasswordEncoder(25, 50, 1, 12, 4);
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
//            System.out.println(userOfApplication.getPassword());
            userOfApplication.setPassword(encoder.encode(userOfApplication.getPassword()));
//            System.out.println(userOfApplication.getPassword());
            userOfApplication.setRole("ROLE_USER");
            userRepository.save(userOfApplication);
//            Optional<UserOfApplication> user1 = userRepository.findByUsername(userOfApplication.getUsername());
//            System.out.println(user1.get().getPassword());
            return true;
        }
    }

    public boolean checkUserForLogin(UserOfApplication userOfApplication){

        if (userRepository.findByUsername(userOfApplication.getUsername()).isPresent()){
            Optional<UserOfApplication> userOfApplication1 = userRepository.findByUsername(userOfApplication.getUsername());
            if (encoder.matches(userOfApplication.getPassword(), userOfApplication1.get().getPassword())){
                return false;
            }else {
                return true;
            }
        }else{
            return true;
        }
    }

}