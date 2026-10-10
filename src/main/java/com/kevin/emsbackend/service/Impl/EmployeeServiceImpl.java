package com.kevin.emsbackend.service.Impl;

import com.kevin.emsbackend.dto.EmployeeDto;
import com.kevin.emsbackend.entity.Employee;
import com.kevin.emsbackend.exception.ResourceNotFoundException;
import com.kevin.emsbackend.mapper.EmployeeMapper;
import com.kevin.emsbackend.repository.EmployeeRepository;
import com.kevin.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee Not Exist with given ID: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not exists with given ID:" + employeeId)
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeOjb = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeOjb);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not exists with given ID:" + employeeId)
        );

        employeeRepository.deleteById(employeeId);

    }

}
