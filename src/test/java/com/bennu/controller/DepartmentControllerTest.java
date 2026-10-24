package com.bennu.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bennu.exception.ResourceNotFoundException;
import com.bennu.model.Department;
import com.bennu.service.DepartmentService;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    void shouldReturn200WhenDepartmentIsFound() throws Exception {
        Department dep = new Department();
        dep.setDepartmentId(4L);
        dep.setName("Ventas");
        
        Mockito.when(departmentService.readById(4L)).thenReturn(dep);

        // ejecución y verificación
        mockMvc.perform(get("/api/department/4") // Ejecuta un GET simulado
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // validar: HTTP 200 OK
                .andExpect(jsonPath("$.departmentId").value(4L)) // debe tener el ID indicado
                .andExpect(jsonPath("$.name").value("Ventas")); // debe tener el nombre indicado
    }

    @Test
    void shouldReturn404WhenDepartmentIsNotFound() throws Exception {
        Mockito.when(departmentService.readById(99L))
            .thenThrow(new ResourceNotFoundException("No se encontro al departamento con ID: 99"));

        // ejecución y verificación
        mockMvc.perform(get("/api/department/99") // Ejecuta un GET simulado
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // validar: HTTP 404 Not Found
                .andExpect(jsonPath("$.status").value(404)) // debe tener el status indicado
                .andExpect(jsonPath("$.error").value("Recurso no encontrado")) // debe tener error indicado
                .andExpect(jsonPath("$.message").value("No se encontro al departamento con ID: 99"));
    }
}
