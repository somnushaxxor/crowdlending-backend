package com.example.crowdlending.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.crowdlending.repository.UserRepository;
import com.example.crowdlending.repository.model.User;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format("User with email \"%s\" not found", email)));
        return ApplicationUser.fromUser(user);
    }
}
