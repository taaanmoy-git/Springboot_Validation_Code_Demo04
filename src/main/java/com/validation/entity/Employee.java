package com.validation.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.validation.dto.EmployeeDTO;

import jakarta.persistence.*; // Use javax.persistence.* if using older versions

@Entity
@Table(name = "Employee")
public class Employee {
	// Auto-increment primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "emp_id", nullable = false, updatable = false)
    @JsonProperty("empId")
    private Long empId;

    @Column(name = "emp_name") 
    @JsonProperty("empName")
    private String empName;

    @Column(name = "emp_age")
    @JsonProperty("empAge")
    private Integer empAge;

    @Column(name = "emp_city")
    @JsonProperty("empCity")
    private String empCity;
    
    @Column(name = "email_id")
    @JsonProperty("emailId")
    private String emailId;
    
    @Column(name = "date_of_birth")
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;
    
    public Employee() {
    	
    }

	public Employee(Long empId, String empName, Integer empAge, String empCity, String emailId, LocalDate dateOfBirth) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empAge = empAge;
		this.empCity = empCity;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getEmpAge() {
		return empAge;
	}

	public void setEmpAge(Integer empAge) {
		this.empAge = empAge;
	}

	public String getEmpCity() {
		return empCity;
	}

	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empAge=" + empAge + ", empCity=" + empCity
				+ ", emailId=" + emailId + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	// Fixed DTO conversion method, Convert Employee to EmployeeDTO
	public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
            employee.getEmpId(),
            employee.getEmpName(),
            employee.getEmpAge(),
            employee.getEmpCity(),
            employee.getEmailId(),
            employee.getDateOfBirth()
        );
    }
}


