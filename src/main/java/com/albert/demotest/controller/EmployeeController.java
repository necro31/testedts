package com.albert.demotest.controller;

import com.albert.demotest.dto.CreateOrUpdateEmployee;
import com.albert.demotest.dto.DeleteEmployee;
import com.albert.demotest.dto.EmployeeDTO;
import com.albert.demotest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public EmployeeDTO createNewEmployee(@RequestBody CreateOrUpdateEmployee createOrUpdateEmployee) {
        return employeeService.createNewEmployee(createOrUpdateEmployee);
    }

    @PutMapping("/update/{id}")
    public EmployeeDTO updateEmployeeById(@PathVariable Long id, @RequestBody CreateOrUpdateEmployee createOrUpdateEmployee) {
        return employeeService.updateEmployee(id, createOrUpdateEmployee);
    }

    @GetMapping("/get-all")
    public List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @DeleteMapping("/bulk-delete")
    public void deleteBulkEmployees(@RequestBody List<Long> id) {
        employeeService.deleteBulkEmployees(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
}
