package com.empmarket.employmentmarketplace.dto;

import com.empmarket.employmentmarketplace.enums.GenderEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private GenderEnum gender;

    private String address;

    private int age;

    private Instant createdAt;

    private Instant updatedAt;

    private SignupDto.CompanyUser company;

}
