package com.validation.service;

import java.util.List;
import com.validation.dto.EmployeeDTO;
import com.validation.exception.EmployeeAlreadyExistException;
import com.validation.exception.EmployeeNotFoundException;

public interface EmployeeService {
	
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws EmployeeAlreadyExistException;
    public List<EmployeeDTO> getEmployees() throws EmployeeNotFoundException;
    public EmployeeDTO getEmployeeById(Long empId) throws EmployeeNotFoundException;
    public List<EmployeeDTO> getEmployeesByName(String empName) throws EmployeeNotFoundException;
    public String updateEmployeeByID(Long empId,EmployeeDTO employeeDTO) throws EmployeeNotFoundException;
	public String deleteEmployeeById(Long empId) throws EmployeeNotFoundException;
}
