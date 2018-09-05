package userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import userservice.model.UserEntity;
import userservice.repository.UserRepository;

@Service
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity updatePasswordByEmail(String login, String password) {
        UserEntity userEntity = userRepository.getUserByEmail(login);
        if (userEntity == null) {
            return null;
        } else {
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
            return userEntity;
        }
    }
}
