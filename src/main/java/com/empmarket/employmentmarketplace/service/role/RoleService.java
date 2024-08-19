package com.empmarket.employmentmarketplace.service.role;

import com.empmarket.employmentmarketplace.dto.req.RoleDto;
import com.empmarket.employmentmarketplace.entity.Role;

public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    Role getRoleById(Long id);

    RoleDto updateRole(Long id, RoleDto roleDto);

    void deleteRole(Long roleId);

}
