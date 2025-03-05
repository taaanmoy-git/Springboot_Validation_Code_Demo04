package com.validation.demo4;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.validation.controller.EmployeeController;
import com.validation.dto.EmployeeDTO;
import com.validation.exception.EmployeeNotFoundException;
import com.validation.service.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DemoTest01 {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    @DisplayName("Employee Found Test1:")
    public void fetchEmployeeById_ShouldReturnEmployee_WhenEmployeeExists() throws EmployeeNotFoundException {
        // Given
        long empId = 1L;
        EmployeeDTO mockEmployee = new EmployeeDTO(
                empId,
                "John Doe",
                30,
                "New York",
                "john.doe@example.com",
                LocalDate.of(1994, 5, 20)
        );

        // Mocking service behavior
        Mockito.when(employeeService.getEmployeeById(empId)).thenReturn(mockEmployee);

        // When
        ResponseEntity<EmployeeDTO> response = employeeController.fetchEmployeeById(empId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmpId()).isEqualTo(empId);
        assertThat(response.getBody().getEmpName()).isEqualTo("John Doe");
        assertThat(response.getBody().getEmpCity()).isEqualTo("New York");
    }

    @Test
    @DisplayName("Employee Not Found Test2:")
    public void fetchEmployeeById_ShouldThrowException_WhenEmployeeNotExists() throws EmployeeNotFoundException {
        // Given
        long empId = 99L;

        // Mock service layer to throw exception
        Mockito.when(employeeService.getEmployeeById(empId))
                .thenThrow(new EmployeeNotFoundException("Employee not found"));

        // When & Then
        EmployeeNotFoundException thrown = assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeController.fetchEmployeeById(empId)
        );

        assertThat(thrown.getMessage()).isEqualTo("Employee not found");
    }
}
