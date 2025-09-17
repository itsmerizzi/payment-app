package com.itsmerizzi.payment_system.service;

import com.itsmerizzi.payment_system.dto.UserResponse;
import com.itsmerizzi.payment_system.entity.User;
import com.itsmerizzi.payment_system.repository.UserRepository;
import com.itsmerizzi.payment_system.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public UserResponse registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String randomCode = RandomString.generateRandomString(64);

        user.setPassword(encodedPassword);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPassword()
        );

        mailService.sendVerificationEmail(user);

        return userResponse;
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        }

        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }

}
