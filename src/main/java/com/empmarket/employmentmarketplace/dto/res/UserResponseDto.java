package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.dto.req.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private List<UserDto> users;

    private Integer totalPages;

    private Integer pageNumber;
}
