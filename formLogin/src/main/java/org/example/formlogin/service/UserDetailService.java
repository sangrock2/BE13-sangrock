package org.example.formlogin.service;

import lombok.RequiredArgsConstructor;
import org.example.formlogin.config.security.CustomUserDetails;
import org.example.formlogin.domain.entity.User;
import org.example.formlogin.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
