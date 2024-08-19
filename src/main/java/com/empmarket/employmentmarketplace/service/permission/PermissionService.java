package com.empmarket.employmentmarketplace.service.permission;

import com.empmarket.employmentmarketplace.dto.req.PermissionDto;
import com.empmarket.employmentmarketplace.entity.Permission;

public interface PermissionService {

    PermissionDto createPermission(PermissionDto permissionDto);

    Permission getPermissionById(Long id);

    boolean updatePermission(Long permissionId, PermissionDto permissionDto);

    void deletePermission(Long permissionId);

}
