package com.bennu.model;

import com.bennu.model.groups.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Department {
    @NotNull(
        message = "La propiedad 'departmentId' es obligatoria para esta petición",
        groups = OnUpdate.class
    )
    private Long departmentId;

    @NotBlank(message = "La propiedad 'name' es obligatoria y no vacía.")
    @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "La propiedad 'name' debe estar conformada solo por letras."
    )
    private String name;
}
