package com.example.crowdlending.service;

import com.example.crowdlending.payload.request.SignupRequest;
import com.example.crowdlending.payload.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.crowdlending.repository.UserRepository;
import com.example.crowdlending.repository.mapper.CrowdfundingMapper;
import com.example.crowdlending.repository.model.User;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrowdfundingMapper mapper;
    private final PasswordEncoder encoder;

    public UserInfoResponse registerUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already taken");
        }
        user = this.saveUser(user);
        return mapper.exportUser(user);
    }

    private User saveUser(User user) {
        user.setEmail(user.getEmail().toLowerCase(Locale.ENGLISH));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(SignupRequest updateInfo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findUserByEmailIgnoreCase(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", auth.getName())));

        //this.checkPasswordsForMismatch(user.getPassword(), updateInfo.getPassword());

        user.setName(updateInfo.getName());
        user.setPassword(updateInfo.getPassword());

        user = this.saveUser(user);

        return mapper.exportUser(user);
    }

//    private void checkPasswordsForMismatch(String oldPassword, String newPassword) {
//        if (encoder.matches(newPassword, oldPassword)) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "New and old passwords must be different"
//            );
//        }
//    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", email)));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with id=%d not found", id)));
    }

    public UserInfoResponse findUserDTOById(Long id) {
        User user = this.findUserById(id);
        return mapper.exportUser(user);
    }

    public UserInfoResponse findUserDTOAfterAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.findUserByEmail(auth.getName());
        return mapper.exportUser(user);
    }
}
