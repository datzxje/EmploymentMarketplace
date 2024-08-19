package com.empmarket.employmentmarketplace.service.user;

import com.empmarket.employmentmarketplace.dto.req.SignupDto;
import com.empmarket.employmentmarketplace.dto.req.UserDto;
import com.empmarket.employmentmarketplace.dto.res.UserResponseDto;
import com.empmarket.employmentmarketplace.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(SignupDto signupDto);

    UserDto getUserById(Long userID);

    UserResponseDto getAllUsers(int pageNumber);

    boolean updateUser(Long userID, UserDto userDto);

    void deleteUser(Long userID);

    List<UserDto> searchUsers(String name, String email, String predicateType,
                             int pageNumber, int pageSize, String sortBy, String sortDir);

    User getUserByEmail(String email);

    void updateUserToken(String token, String email);

    User getUserByRefreshTokenAndEmail(String token, String email);
}
