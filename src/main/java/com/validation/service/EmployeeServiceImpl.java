package com.validation.service;

/*
 * ************************         vvi      ***************************************************  
  Note 1: When want to save data to table, first it comes the data from api,so it is dto obj data,
   		  so we convert dto object data to entity object data then we save it to the table.
  Note 2: When want to show data to the api or post man always show dto object / not entity obj
				So we convert entity obj to dto object then send it to api
************************************************************************************************
*/
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.validation.dto.EmployeeDTO;
import com.validation.entity.Employee;
import com.validation.exception.EmployeeAlreadyExistException;
import com.validation.exception.EmployeeNotFoundException;
import com.validation.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository repository;

	// Create Employee
	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws EmployeeAlreadyExistException {
		// Finding Employee By Their email
		Optional<Employee> emp = repository.findByEmailId(employeeDTO.getEmailId());
		if (emp.isPresent()) {
			throw new EmployeeAlreadyExistException("Employee email id already exist:" + employeeDTO.getEmailId());
		} else {
			Employee employee = repository.save(employeeDTO.toEntity());
			// Converting Entity to DTO
			return Employee.toDTO(employee);
		}
	}

	// Fetch all Employee
	@Override
	public List<EmployeeDTO> getEmployees() throws EmployeeNotFoundException {
		// Converting Entity Obj to DTO Obj, after showing to API
		// repository.findAll(), It we get Entity Obj , need to convert to DTO
		// =map(Employee::toDTO)
		List<EmployeeDTO> employees = repository.findAll().stream().map(Employee::toDTO).collect(Collectors.toList());
		if (employees.isEmpty()) {
			throw new EmployeeNotFoundException("No employees found. Table is empty");
		}
		return employees;
	}

	// Fetch Employee by Name
	@Override
	public List<EmployeeDTO> getEmployeesByName(String empName) throws EmployeeNotFoundException {
		List<EmployeeDTO> employees = repository.findByEmpName(empName).stream().map(Employee::toDTO)
				.collect(Collectors.toList());
		if (employees.isEmpty()) {
			throw new EmployeeNotFoundException("No employees found with name: " + empName);
		}
		return employees;
	}

	// Fetch Employee by Id
	@Override
	public EmployeeDTO getEmployeeById(Long empId) throws EmployeeNotFoundException {
		// repository.findById(empId) will return Optional <Employee>
		// Converting Optional <Employee> to Employee = .orElseThrow()
		Employee employee = repository.findById(empId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + empId));
		// Converting Entity To DTO
		return Employee.toDTO(employee);
	}

	// Update Employee
	@Override
	public String updateEmployeeByID(Long empId, EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
		Optional<Employee> employeeOptional = repository.findById(empId);
		// If employee does not exist, throw an exception
		if (!employeeOptional.isPresent()) {
			throw new EmployeeNotFoundException("Employee not found with id: " + empId);
		}
		// Validate that empId matches the DTO's empId (to prevent changes)
		if (!empId.equals(employeeDTO.getEmpId())) {
			return "Employee ID cannot be changed.";
		}
		// Update employee details
		Employee emp = employeeOptional.get();
		emp.setEmpName(employeeDTO.getEmpName());
		emp.setEmpAge(employeeDTO.getEmpAge());
		emp.setEmpCity(employeeDTO.getEmpCity());
		emp.setEmailId(employeeDTO.getEmailId());
		emp.setDateOfBirth(employeeDTO.getDateOfBirth());
		// Save emp Entity obj
		repository.save(emp);
		return "Employee updated successfully, empId: " + empId;
	}

	// Delete employee by id
	@Override
	public String deleteEmployeeById(Long empId) throws EmployeeNotFoundException {
		// Fetch employee before deleting
		Optional<Employee> employee = repository.findById(empId);
		if (employee.isPresent()) {
			repository.deleteById(empId);
			return "Deleted Employee details:" + employee.get().toString();
		} else {
			throw new EmployeeNotFoundException("Employee not found with id:" + empId);
		}
	}
}
