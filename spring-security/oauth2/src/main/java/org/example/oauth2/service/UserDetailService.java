package org.example.token.service;

import lombok.RequiredArgsConstructor;
import org.example.token.config.security.CustomUserDetails;
import org.example.token.domain.entity.User;
import org.example.token.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder().user(user).build();
    }
}
