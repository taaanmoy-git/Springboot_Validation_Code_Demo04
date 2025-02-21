package com.validation.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.validation.entity.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


public class EmployeeDTO {
	
	private Long empId;
    
    @NotNull(message= "{employee.empname.absent}")
    @Pattern(regexp="^[A-Za-z]+( [A-Za-z]+)*", message="{employee.empname.absent}")
    private String empName;
    
    @NotNull(message="{employee.empage.absent}")
    @Min(value = 18, message="{employee.empage.mininvalid}")
    @Max(value = 60, message="{employee.empage.maxinvalid}")
    private Integer empAge;
    
    private String empCity;
    
    @NotNull(message="{employee.emailid.absent}")
    @Email(message="employee.emailid.invalid")
    private String emailId;
    
    @NotNull(message="{employee.dob.absent}")
    @Past(message="{employee.dob.invalid}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    
    public EmployeeDTO() {
    	
    }

	public EmployeeDTO(Long empId, String empName, Integer empAge, String empCity, String emailId,
			LocalDate dateOfBirth) {
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
	
	// ✅ Convert EmployeeDTO to Employee Entity
    public Employee toEntity() {
        return new Employee(
            this.empId,
            this.empName,
            this.empAge,
            this.empCity,
            this.emailId,
            this.dateOfBirth
        );
    }

}


