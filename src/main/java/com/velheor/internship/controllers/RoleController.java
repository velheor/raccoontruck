package com.velheor.internship.controllers;

import com.velheor.internship.dto.RoleViewDTO;
import com.velheor.internship.mappers.RoleMapper;
import com.velheor.internship.service.RoleService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping("/{id}")
    public RoleViewDTO findById(@PathVariable("id") UUID id) {
        return roleMapper.roleToRoleDto(roleService.findById(id));
    }

    @PutMapping
    public RoleViewDTO update(@RequestBody RoleViewDTO roleViewDTO) {
        return roleMapper.roleToRoleDto(roleService.save(roleMapper.roleDtoToRole(roleViewDTO)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleViewDTO save(@RequestBody RoleViewDTO roleViewDTO) {
        return roleMapper.roleToRoleDto(roleService.save(roleMapper.roleDtoToRole(roleViewDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") UUID id) {
        roleService.deleteById(id);
    }
}
