package com.bennu.model;

import com.bennu.model.groups.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// POJO -> Plain Old Java Object
// no requiere anotaciones
// PERO puede necesitarse para validación de datos (jakarta) y/o ahorrar código (lombok)

@Getter @Setter
public class Employee {

    @NotNull(
        message = "La propiedad 'employeeId' es obligatoria para esta petición",
        groups = OnUpdate.class
    )
    private Long employeeId;

    @NotNull(message = "La propiedad 'departmentId' es obligatoria.")
    private Long departmentId;

    // no validations required
    private Long bossId;

    @NotBlank(message = "La propiedad 'name' es obligatoria y no vacía.")
    @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "La propiedad 'name' debe estar conformada solo por letras."
    )
    private String name;
    
    @NotNull(message = "La propiedad 'salary' es obligatoria.")
    private Double salary;
}
