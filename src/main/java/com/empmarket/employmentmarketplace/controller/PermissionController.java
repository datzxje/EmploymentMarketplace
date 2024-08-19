package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.PermissionDto;
import com.empmarket.employmentmarketplace.service.permission.PermissionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/permissions")
    public ResponseEntity<?> createPermission(@Valid @RequestBody PermissionDto permissionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.createPermission(permissionDto));
    }

    @GetMapping("/permission/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(permissionService.getPermissionById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Permission not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get permission by id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/permission/{id}")
    public ResponseEntity<?> updatePermission(@Valid @PathVariable Long id, @RequestBody PermissionDto permissionDto) {
        boolean success = permissionService.updatePermission(id, permissionDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body(permissionDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/permission/{id}")
    public ResponseEntity<?> deletePermission(Long id) {
        try {
            permissionService.deletePermission(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Permission not found",HttpStatus.NOT_FOUND);
        }
    }

}
