package com.hanghae.user.service;

import com.hanghae.common.exception.ResourceNotFoundException;
import com.hanghae.user.domain.Role;
import com.hanghae.user.domain.User;
import com.hanghae.user.dto.UserRegistrationRequestDto;
import com.hanghae.user.dto.UserResponseDto;
import com.hanghae.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRegistrationRequestDto request) {
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                Role.USER
        );
        userRepository.save(user);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}