package org.example.formlogin.service;

import lombok.RequiredArgsConstructor;
import org.example.formlogin.domain.entity.User;
import org.example.formlogin.domain.repository.UserRepository;
import org.example.formlogin.dto.SignUpRequestDto;
import org.example.formlogin.exception.DuplicateUserIdException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequestDto dto) {
        if (userRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용 중인 아이디입니다.");
        }

        User user = dto.toUser(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }
}
