package com.empmarket.employmentmarketplace.service.user;

import com.empmarket.employmentmarketplace.dto.SignupDto;
import com.empmarket.employmentmarketplace.dto.UserDto;
import com.empmarket.employmentmarketplace.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserDto createUser(SignupDto signupDto);

    UserDto getUserById(Long userID);

    UserResponseDto getAllUsers(int pageNumber);

    boolean updateUser(Long userID, UserDto userDto);

    void deleteUser(Long userID);

    List<UserDto> searchUsers(String name, String email, String predicateType,
                             int pageNumber, int pageSize, String sortBy, String sortDir);

}
