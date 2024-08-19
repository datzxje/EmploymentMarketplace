package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.entity.Role;
import lombok.*;

@Data
public class LoginResponseDto {

    private String accessToken;

    private UserLogin user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {

        private Long id;

        private String email;

        private String name;

        private Role role;
    }
}
