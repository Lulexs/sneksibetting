package com.luka.sneksibetting.services;

import com.luka.sneksibetting.models.user.User;
import com.luka.sneksibetting.models.user.UserInfo;
import com.luka.sneksibetting.models.user.UserLoginCreds;
import com.luka.sneksibetting.models.user.UserRegisterCreds;
import org.springframework.stereotype.Service;
import com.luka.sneksibetting.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserInfo> loginUser(UserLoginCreds creds) {
        Optional<User> user = userRepository.getUserByUsername(creds.username());
        if (user.isEmpty() || !creds.password().equals(user.get().password())) {
            return Optional.empty();
        }
        return Optional.of(UserInfo.from(user.get()));
    }

    public UserInfo registerUser(UserRegisterCreds creds) {
        return UserInfo.from(userRepository.registerUser(creds));
    }
}
