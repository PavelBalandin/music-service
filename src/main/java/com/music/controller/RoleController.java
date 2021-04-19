package com.music.controller;

import com.music.model.Role;
import com.music.repository.RoleRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRole() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id) {
        Role role = RestPreconditions.checkFound(roleRepository.findById(id).orElse(null));
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        roleRepository.save(role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long id, @RequestBody Role role) {
        Role roleFromDb = RestPreconditions.checkFound(roleRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(role, roleFromDb, "id");
        roleRepository.save(roleFromDb);
        return new ResponseEntity<>(roleFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable("id") Long id) {
        Role role = RestPreconditions.checkFound(roleRepository.findById(id).orElse(null));
        roleRepository.delete(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
