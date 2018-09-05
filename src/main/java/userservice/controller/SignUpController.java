package userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.forms.UserFormSignUp;
import userservice.model.UserEntity;
import userservice.service.SignUpService;

@RestController
@RequestMapping(value = "/signUp")
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping
    public ResponseEntity<?> postSignUp(@RequestBody UserFormSignUp userFormSignUp) {
        UserEntity user = signUpService.saveUser(userFormSignUp);
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
