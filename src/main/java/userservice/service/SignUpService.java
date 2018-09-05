package userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import userservice.forms.UserFormSignUp;
import userservice.model.Role;
import userservice.model.UserEntity;
import userservice.repository.UserRepository;

@Service
public class SignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity saveUser(UserFormSignUp userFormSignUp) {
        if (userRepository.getUserByEmail(userFormSignUp.getEmail()) == null) {
            UserEntity userEntity = UserEntity
                    .builder()
                        .email(userFormSignUp.getEmail())
                        .password(passwordEncoder.encode(userFormSignUp.getPassword()))
                        .name(userFormSignUp.getName())
                        .surname(userFormSignUp.getSurname())
                        .role(Role.USER)
                    .build();
            userRepository.save(userEntity);
            return userEntity;
        } else { return null; }
    }
}
