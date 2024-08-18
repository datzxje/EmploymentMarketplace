package com.empmarket.employmentmarketplace.entity;

import com.empmarket.employmentmarketplace.dto.SignupDto;
import com.empmarket.employmentmarketplace.dto.UserDto;
import com.empmarket.employmentmarketplace.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@EntityListeners(AuditTrailListener.class)
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String address;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT + 7")
    private Instant createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT + 7")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail(email);
        userDto.setName(name);
        userDto.setAddress(address);
        userDto.setGender(gender);
        userDto.setAge(age);
        userDto.setCreatedAt(createdAt);
        userDto.setUpdatedAt(updatedAt);

        if (company != null) {
            SignupDto.CompanyUser companyUser = new SignupDto.CompanyUser();
            companyUser.setId(company.getId());
            companyUser.setName(company.getName());
            userDto.setCompany(companyUser);
        }

        return userDto;
    }
}
