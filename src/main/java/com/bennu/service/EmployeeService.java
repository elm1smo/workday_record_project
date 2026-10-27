package com.bennu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bennu.exception.ResourceNotFoundException;
import com.bennu.mapper.EmployeeMapper;
import com.bennu.model.Employee;

@Service // Indica a Spring que esta clase debe tratarse como servicio
public class EmployeeService {
    private @Autowired  EmployeeMapper employeeMapper;

    // Servicio crear empleado (=> debería devolver el ID generado)
    @Transactional 
    public void create(Employee employee) {
        if(employeeMapper.exists(employee.getEmployeeId())) {
            throw new DataIntegrityViolationException("El empleado ya existe");
        }

        employeeMapper.create(employee);
    }

    // Servicio obtener todos los empleados
    public List<Employee> readAll() {
        return employeeMapper.readAll(); 
    }

    // Servicio obtener un empleado
    public Employee readById(Long id) {
        Employee employeeFound = employeeMapper.readById(id);
        if(employeeFound == null) {
            throw new ResourceNotFoundException("No se encontró al empleado con ID: " + id);
        }

        return employeeFound;
    }

    // Servicio actualizar empleado
    @Transactional 
    public Employee update(Employee employee) {
        if(!employeeMapper.exists(employee.getEmployeeId())) {
            throw new ResourceNotFoundException("No se encontró al empleado con ID: " + employee.getEmployeeId());
        }

        employeeMapper.update(employee);
        
        return employee;
    }

    // Servicio borrar empleado
    @Transactional 
    public Employee delete(Long id) {
        if(!employeeMapper.exists(id)) {
            throw new ResourceNotFoundException("No se encontró al empleado con ID: " + id);
        }
        
        Employee employeeToDelete = employeeMapper.readById(id);
        employeeMapper.delete(id);

        return employeeToDelete;
    }
}
