package userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.common.Constants;
import userservice.common.JWTUtils;
import userservice.forms.UserFormSignUp;
import userservice.model.UserEntity;
import userservice.repository.UserRepository;
import userservice.service.EmailService;
import userservice.service.ResetPasswordService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ResetPasswordController {

    private final EmailService mailService;
    private final UserRepository userRepository;
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordController(EmailService mailService, UserRepository userRepository, ResetPasswordService resetPasswordService) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.resetPasswordService = resetPasswordService;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity<?> resetRequest(@RequestBody UserFormSignUp userFormSignUp) {
        if (userRepository.getUserByEmail(userFormSignUp.getEmail()) == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            mailService.sendMail(userFormSignUp.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "do-reset-password", method = RequestMethod.PUT)
    public ResponseEntity<?> putNewPasswordRequest(@RequestBody UserFormSignUp userFormSignUp, HttpServletRequest request) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        final String email = JWTUtils.getAudience(token).get(1);
        UserEntity userEntity = resetPasswordService.updatePasswordByEmail(email, userFormSignUp.getPassword());
        if (userEntity==null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}