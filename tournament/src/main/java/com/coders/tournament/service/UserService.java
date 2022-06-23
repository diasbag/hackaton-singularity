package com.coders.tournament.service;

import com.coders.tournament.model.User;
import com.coders.tournament.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signUp(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);
        return "Created";
    }

    public Long signIn(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login, password);
        if (user == null) {
            throw new RuntimeException("Invalid value!!!");
        }
        return user.getId();
    }
}
