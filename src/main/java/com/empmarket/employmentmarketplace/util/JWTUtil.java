package com.empmarket.employmentmarketplace.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JWTUtil {

    private final JwtEncoder jwtEncoder;

    public static final MacAlgorithm JWT_ALGO = MacAlgorithm.HS256;

    @Value("${jwt.token-validity-time}")
    private Long jwtExpiredTime;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant validity = now.plus(jwtExpiredTime, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("authorities", authentication)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGO).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }
}
