package com.empmarket.employmentmarketplace.service.permission;

import com.empmarket.employmentmarketplace.dto.req.PermissionDto;
import com.empmarket.employmentmarketplace.entity.Permission;
import com.empmarket.employmentmarketplace.mapper.PermissionMapper;
import com.empmarket.employmentmarketplace.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper = PermissionMapper.INSTANCE;

    public PermissionDto createPermission(PermissionDto permissionDto) {
        Permission permission = permissionMapper.toEntity(permissionDto);

        if (!isPermissionExist(permission)) {
            throw new IllegalArgumentException("Invalid PermissionId provided.");
        }

        Permission savedPermission = permissionRepository.save(permission);

        return permissionMapper.toDto(savedPermission);
    }

    public Permission getPermissionById(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.orElse(null);
    }

    public boolean updatePermission(Long permissionId, PermissionDto permissionDto) {
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (optionalPermission.isPresent()) {

            Permission permission = optionalPermission.get();

            permissionMapper.updatePermissionFromDto(permissionDto, permission);

            permissionRepository.save(permission);

            return true;
        }
        return false;
    }

    public void deletePermission(Long permissionId) {
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (optionalPermission.isPresent()) {
            Permission permission = optionalPermission.get();
            permission.getRoles().forEach(role -> role.getPermissions().remove(permission));

            permissionRepository.delete(permission);
        }
        else throw new IllegalArgumentException("Invalid PermissionId provided.");
    }

    public boolean isPermissionExist(Permission permission) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(
            permission.getModule(), permission.getApiPath(), permission.getMethod()
        );
    }

}
