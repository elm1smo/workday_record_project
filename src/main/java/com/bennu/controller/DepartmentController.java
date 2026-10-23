package com.bennu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bennu.model.Department;
import com.bennu.service.DepartmentService;

@RestController 
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    // Inyección del servicio
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    // Endpoint crear departmento: POST http://localhost:8080/api/department
    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        departmentService.create(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED); // 201 Created
    }

    // Endpoint obtener todos los departmentos: GET http://localhost:8080/api/department
    @GetMapping
    public ResponseEntity<List<Department>> readAll() {
        List<Department> departments = departmentService.readAll();
        return new ResponseEntity<>(departments, HttpStatus.OK); // 200 OK
    }

    // Endpoint obtener un departmento: GET http://localhost:8080/api/department/{id}
    @GetMapping("/{id}") // indica que es un endpoint GET
    public ResponseEntity<Department> readById(@PathVariable Long id) {
        Department department = departmentService.readById(id);
        return new ResponseEntity<>(department, HttpStatus.OK); // 200 OK
    }

    // Endpoint actualizar departmento: PUT http://localhost:8080/api/department/{id}
    @PutMapping
    public ResponseEntity<Department> update(@RequestBody Department department) {
        Department updatedDepartment = departmentService.update(department);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    // Endpoint borrar departmento: DELETE http://localhost:8080/api/department/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Department> delete(@PathVariable Long id) {
        Department departmentToDelete = departmentService.delete(id);
        return new ResponseEntity<>(departmentToDelete, HttpStatus.OK);
    }
}
