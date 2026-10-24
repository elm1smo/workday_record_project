package com.bennu.model;

import lombok.Getter;
import lombok.Setter;

// POJO -> Plain Old Java Object
// no requiere anotaciones
// PERO puede necesitarse para validación de datos (jakarta) y/o ahorrar código (lombok)

@Getter @Setter
public class Employee {
    private Long employeeId;
    private Long departmentId;
    private Long bossId;
    private String name;
    private Double salary;
}
