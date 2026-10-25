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

import com.bennu.constants.Constants;
import com.bennu.exception.ResourceNotFoundException;
import com.bennu.model.Employee;
import com.bennu.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void shouldReturn200WhenEmployeeIsFound() throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId(1L);
        emp.setName("Carlos");
        
        Mockito.when(employeeService.readById(1L)).thenReturn(emp);

        // ejecución y verificación
        mockMvc.perform(get(Constants.BASE_API + "/employee/1") // Ejecuta un GET simulado
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // validar: HTTP 200 OK
                .andExpect(jsonPath("$.employeeId").value(1L)) // debe tener el ID indicado
                .andExpect(jsonPath("$.name").value("Carlos")); // debe tener el nombre indicado
    }

    @Test
    void shouldReturn404WhenEmployeeIsNotFound() throws Exception {
        Mockito.when(employeeService.readById(99L))
            .thenThrow(new ResourceNotFoundException("No se encontro al empleado con ID: 99"));

        // ejecución y verificación
        mockMvc.perform(get(Constants.BASE_API + "/employee/99") // Ejecuta un GET simulado
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // validar: HTTP 404 Not Found
                .andExpect(jsonPath("$.status").value(404)) // debe tener el status indicado
                .andExpect(jsonPath("$.error").value("Recurso no encontrado")) // debe tener error indicado
                .andExpect(jsonPath("$.message").value("No se encontro al empleado con ID: 99"));
    }
}
