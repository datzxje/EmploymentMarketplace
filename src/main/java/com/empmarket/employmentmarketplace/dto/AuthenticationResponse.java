package com.empmarket.employmentmarketplace.dto;

import lombok.*;

@Data
public class AuthenticationResponse {

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
    }
}
