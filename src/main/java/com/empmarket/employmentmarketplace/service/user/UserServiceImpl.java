package com.empmarket.employmentmarketplace.service.user;

import com.empmarket.employmentmarketplace.dto.SignupDto;
import com.empmarket.employmentmarketplace.dto.UserDto;
import com.empmarket.employmentmarketplace.dto.UserResponseDto;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(SignupDto signupDto) {
        String hashPassword = passwordEncoder.encode(signupDto.getPassword());
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(hashPassword);

        User savedUser = userRepository.save(user);
        return savedUser.getUserDto();
    }

    public UserDto getUserById(Long userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getUserDto();
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public UserResponseDto getAllUsers(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<User> userPage = userRepository.findAll(pageable);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setPageNumber(userPage.getPageable().getPageNumber());
        userResponseDto.setTotalPages(userPage.getTotalPages());
        userResponseDto.setUsers(userPage.stream().map(User::getUserDto).collect(Collectors.toList()));

        return userResponseDto;
    }

    public boolean updateUser(Long userID, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());

            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(Long userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userID);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}

