package com.empmarket.employmentmarketplace.service.user;

import com.empmarket.employmentmarketplace.dto.req.SignupDto;
import com.empmarket.employmentmarketplace.dto.req.UserDto;
import com.empmarket.employmentmarketplace.dto.res.UserResponseDto;
import com.empmarket.employmentmarketplace.entity.Company;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.mapper.UserMapper;
import com.empmarket.employmentmarketplace.repository.CompanyRepository;
import com.empmarket.employmentmarketplace.repository.UserRepository;
import com.empmarket.employmentmarketplace.specification.GenericSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final CompanyRepository companyRepository;

    public UserDto createUser(SignupDto signupDto) {
        String hashPassword = passwordEncoder.encode(signupDto.getPassword());
        User user = userMapper.toEntity(signupDto);
        user.setPassword(hashPassword);

        if (user.getCompany() != null) {
            Optional<Company> companyOptional = companyRepository.findById(user.getCompany().getId());
            user.setCompany(companyOptional.orElse(null));
        }

        User savedUser = userRepository.save(user);
        return toUserDto(savedUser);
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

    public List<UserDto> searchUsers(String name, String email, String predicateType,
                                  int pageNumber, int pageSize, String sortBy, String sortDir) {
        GenericSpecification<User> builder = new GenericSpecification<>();
        if (name != null) {
            builder.with("name", "=", name, predicateType);
        }
        if (email != null) {
            builder.with("email", "=", email, predicateType);
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<User> users = userRepository.findAll(builder.build(), pageable).getContent();
        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        SignupDto.CompanyUser companyUser = new SignupDto.CompanyUser();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());

        if(user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            userDto.setCompany(companyUser);
        }
        return userDto;
    }

    public boolean updateUser(Long userID, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            userMapper.updateUserFromDto(userDto, user);

            if (user.getCompany() != null) {
                Optional<Company> companyOptional = companyRepository.findById(user.getCompany().getId());
                user.setCompany(companyOptional.orElse(null));
            }

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUserToken(String token, String email) {
        User currentUser = getUserByEmail(email);
        if(currentUser != null) {
            currentUser.setRefreshToken(token);
            userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return userRepository.findByRefreshTokenAndEmail(token, email);
    }
}

