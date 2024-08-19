package com.empmarket.employmentmarketplace.service.role;

import com.empmarket.employmentmarketplace.dto.req.RoleDto;
import com.empmarket.employmentmarketplace.entity.Permission;
import com.empmarket.employmentmarketplace.entity.Role;
import com.empmarket.employmentmarketplace.mapper.RoleMapper;
import com.empmarket.employmentmarketplace.repository.PermissionRepository;
import com.empmarket.employmentmarketplace.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RoleMapper roleMapper = RoleMapper.INSTANCE;


    public RoleDto createRole(RoleDto roleDto) {
        roleRepository.findByName(roleDto.getName()).ifPresent(role -> {
                    throw new EntityExistsException(role.getName());
                });

        Role roleToSave = roleMapper.toEntity(roleDto);

        List<Permission> permissions = roleToSave.getPermissions() == null ?
                List.of() :
                permissionRepository.findByIdIn(
                        roleToSave.getPermissions().stream().map(Permission::getId).toList()
                );

        roleToSave.setPermissions(permissions);

        Role savedRole = roleRepository.save(roleToSave);
        return roleMapper.toDto(savedRole);
    }

    public Role getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(roleDto.getName()));

        roleMapper.updateRoleFromDto(roleDto, role);

        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
            List<Long> permissionIds = role.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .toList();

            List<Permission> permissions = permissionRepository.findByIdIn(permissionIds);
            role.setPermissions(permissions);
        }

        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    public void deleteRole(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            roleRepository.delete(role);
        }
        else throw new EntityNotFoundException("Invalid RoleId provided.");
    }

}
