package com.validation.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.validation.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query("SELECT e FROM Employee e WHERE e.empName = :empName")
	List<Employee> findByEmpName(String empName);
	
    @Query("SELECT e FROM Employee e WHERE e.emailId = :emailId ")
    Optional<Employee> findByEmailId(String emailId);
}

