package com.empmarket.employmentmarketplace.dto.req;

import com.empmarket.employmentmarketplace.entity.Role;
import com.empmarket.employmentmarketplace.enums.GenderEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
public class SignupDto {

    private String name;

    private String email;

    private String password;

    private GenderEnum gender;

    private String address;

    private int age;

    private Instant createdAt;

    private CompanyUser company;

    private Role role;

    @Getter
    @Setter
    public static class CompanyUser {

        private Long id;

        private String name;
    }

}
