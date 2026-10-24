package com.bennu.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bennu.exception.ResourceNotFoundException;
import com.bennu.mapper.DepartmentMapper;
import com.bennu.model.Department;

@Service 
public class DepartmentService {
    private final DepartmentMapper departmentMapper;

    // Inyección del mapper
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    // Servicio crear departamento
    public void create(Department department) {
        if(departmentMapper.exists(department.getDepartmentId())) {
            throw new DataIntegrityViolationException("El departamento ya existe");
        }

        departmentMapper.create(department);
    }

    // Servicio obtener todos los departamentos
    public List<Department> readAll() {
        return departmentMapper.readAll(); 
    }

    // Servicio obtener un departamento
    public Department readById(Long id) {
        Department departmentFound = departmentMapper.readById(id);
        if(departmentFound == null) {
            throw new ResourceNotFoundException("No se encontró al departamento con ID: " + id);
        }

        return departmentFound;
    }

    // Servicio actualizar departamento
    public Department update(Department department) {
        if(!departmentMapper.exists(department.getDepartmentId())) {
            throw new ResourceNotFoundException("No se encontró al departamento con ID: " + department.getDepartmentId());
        }

        departmentMapper.update(department);
        
        return department;
    }

    // Servicio borrar departamento
    public Department delete(Long id) {
        if(!departmentMapper.exists(id)) {
            throw new ResourceNotFoundException("No se encontró al departamento con ID: " + id);
        }
        
        Department departmentToDelete = departmentMapper.readById(id);
        departmentMapper.delete(id);

        return departmentToDelete;
    }
}
