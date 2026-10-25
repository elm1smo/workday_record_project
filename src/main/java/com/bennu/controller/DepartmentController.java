package com.bennu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bennu.constants.Constants;
import com.bennu.model.Department;
import com.bennu.service.DepartmentService;

@RestController 
@RequestMapping(path = Constants.BASE_API + "/department", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {
    private @Autowired  DepartmentService departmentService;
    
    // Endpoint crear departmento: POST http://localhost:8080/api/v1/department
    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        departmentService.create(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED); // 201 Created
    }

    // Endpoint obtener todos los departmentos: GET http://localhost:8080/api/v1/department
    @GetMapping
    public ResponseEntity<List<Department>> readAll() {
        List<Department> departments = departmentService.readAll();
        return new ResponseEntity<>(departments, HttpStatus.OK); // 200 OK
    }

    // Endpoint obtener un departmento: GET http://localhost:8080/api/v1/department/{id}
    @GetMapping("/{id}") // indica que es un endpoint GET
    public ResponseEntity<Department> readById(@PathVariable Long id) {
        Department department = departmentService.readById(id);
        return new ResponseEntity<>(department, HttpStatus.OK); // 200 OK
    }

    // Endpoint actualizar departmento: PUT http://localhost:8080/api/v1/department
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
