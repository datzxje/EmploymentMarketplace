package com.empmarket.employmentmarketplace.config;

import com.empmarket.employmentmarketplace.entity.Permission;
import com.empmarket.employmentmarketplace.entity.Role;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.service.user.UserService;
import com.empmarket.employmentmarketplace.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PermissionInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Transactional
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String httpMethod = request.getMethod();

        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : null;

        if (email != null && !email.isEmpty()) {
            User user = userService.getUserByEmail(email);
            if (user != null && user.getRole() != null) {
                boolean isAllow = user.getRole().getPermissions().stream()
                        .anyMatch(item -> item.getApiPath().equals(path) && item.getMethod().equals(httpMethod));
                if (!isAllow) {
                    throw new AccessDeniedException("You don't have access to this Endpoint");
                }
            }
        }

        return false;

    }
}
