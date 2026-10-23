package com.bennu.model;

// POJO -> Plain Old Java Object
// no requiere anotaciones
// PERO puede necesitarse para validación de datos (jakarta) y/o ahorrar código (lombok)

public class Employee {
    public Long employeeId;
    public Long departmentId;
    public Long bossId;
    public String name;
    public Double salary;
}
