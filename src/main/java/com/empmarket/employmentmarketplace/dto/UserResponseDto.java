package com.empmarket.employmentmarketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private List<UserDto> users;

    private Integer totalPages;

    private Integer pageNumber;
}
