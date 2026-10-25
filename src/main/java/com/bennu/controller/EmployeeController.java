package com.bennu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bennu.service.EmployeeService;
import com.bennu.constants.Constants;
import com.bennu.model.Employee;

@RestController // Indica a Spring que se manejará peticiones HTTP REST
@RequestMapping(path = Constants.BASE_API + "/employee", produces = MediaType.APPLICATION_JSON_VALUE) // URL base para todos los endpoints
public class EmployeeController {
    private @Autowired  EmployeeService employeeService;
    
    // Endpoint crear empleado: POST http://localhost:8080/api/v1/employee
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED); // 201 Created
    }

    // Endpoint obtener todos los empleados: GET http://localhost:8080/api/v1/employee
    @GetMapping
    public ResponseEntity<List<Employee>> readAll() {
        List<Employee> employees = employeeService.readAll();
        return new ResponseEntity<>(employees, HttpStatus.OK); // 200 OK
    }

    // Endpoint obtener un empleado: GET http://localhost:8080/api/v1/employee/{id}
    @GetMapping("/{id}") // indica que es un endpoint GET
    public ResponseEntity<Employee> readById(@PathVariable Long id) {
        Employee employee = employeeService.readById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK); // 200 OK
    }

    // Endpoint actualizar empleado: PUT http://localhost:8080/api/v1/employee
    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Endpoint borrar empleado: DELETE http://localhost:8080/api/v1/employee/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Long id) {
        Employee employeeToDelete = employeeService.delete(id);
        return new ResponseEntity<>(employeeToDelete, HttpStatus.OK);
    }

}
