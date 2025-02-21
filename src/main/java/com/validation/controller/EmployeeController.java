package com.validation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.validation.dto.EmployeeDTO;
import com.validation.exception.EmployeeAlreadyExistException;
import com.validation.exception.EmployeeNotFoundException;
import com.validation.service.EmployeeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	// Test
	@GetMapping("/employees/ok")
	public String test() {
		return "ok";
	}

	// Create a new employee with ResponseEntity
	// EmployeeAlreadyExistException
	// http://localhost:8080/api/employees
	@PostMapping("/employees")
	public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO)
			throws EmployeeAlreadyExistException {
		
		// Create the new employee
		EmployeeDTO createdEmployee = service.createEmployee(employeeDTO);
		// Create Employee with 201 Status code
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
	}

	// Fetch all employees
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDTO>> fetchEmployees() {
		List<EmployeeDTO> employees = service.getEmployees();
		return ResponseEntity.ok(employees);
	}

	// fetch employees by id
	//EmployeeNotFoundException
	@GetMapping("/employees/id/{empId}")
	public ResponseEntity<EmployeeDTO> fetchEmployeeById(@PathVariable  
			@Min(value = 1, message="{employee.empid.invalid}") 
			@Max(value = 100, message="{employee.empid.invalid}") long empId) 
	        throws EmployeeNotFoundException {
		EmployeeDTO employeeDTO = service.getEmployeeById(empId);
	    return new ResponseEntity<EmployeeDTO>(employeeDTO, HttpStatus.OK);
	}
	
	// Fetch Employees By Name
	//http://localhost:8080/api/employees/name/John Doe
	//EmployeeNotFoundException
    @GetMapping("/employees/name/{empName}")
    public ResponseEntity<List<EmployeeDTO>> fetchEmployeesByName(@PathVariable String empName)
            throws EmployeeNotFoundException {
        List<EmployeeDTO> employees = service.getEmployeesByName(empName);
        return ResponseEntity.ok(employees); // return code 200
    }

	// Update employee by ID
    @PutMapping("/employees/id/{empId}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Long empId, @Valid @RequestBody EmployeeDTO employeeDTO) 
            throws EmployeeNotFoundException {
        String response = service.updateEmployeeByID(empId, employeeDTO);
        return ResponseEntity.ok(response);
    }

	// delete employee by id
	@DeleteMapping("/employees/id/{empId}")
	public String deleteEmployeeById(@PathVariable long empId) {
		return service.deleteEmployeeById(empId);
	}

}
