package com.albert.demotest.service;

import com.albert.demotest.dto.CreateOrUpdateEmployee;
import com.albert.demotest.dto.EmployeeDTO;
import com.albert.demotest.entity.MsEmployee;
import com.albert.demotest.enums.Grade;
import com.albert.demotest.repository.MsEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final MsEmployeeRepository msEmployeeRepository;


    public EmployeeDTO createNewEmployee(CreateOrUpdateEmployee createOrUpdateEmployee) {
        // asumsi nama employee boleh duplicate

        final MsEmployee newEmployee = msEmployeeRepository.save(
                new MsEmployee()
                        .setName(createOrUpdateEmployee.getName())
                        .setSalary(createOrUpdateEmployee.getSalary())
                        .setGrade(createOrUpdateEmployee.getGrade())
        );

        return buildData(newEmployee);
    }


    public EmployeeDTO updateEmployee(Long id, CreateOrUpdateEmployee createOrUpdateEmployee) {
        final MsEmployee existingEmployee = msEmployeeRepository.findByDeletedAtIsNullAndId(id)
                .orElseThrow(() -> new RuntimeException("The id was not found"));

        msEmployeeRepository.save(
                existingEmployee
                        .setName(createOrUpdateEmployee.getName())
                        .setSalary(createOrUpdateEmployee.getSalary())
                        .setGrade(createOrUpdateEmployee.getGrade())
        );

        return buildData(existingEmployee);
    }

    public void deleteBulkEmployees(List<Long> ids) {
        final List<MsEmployee> existingEmployee = msEmployeeRepository.findByDeletedAtIsNullAndIdIn(ids);

        for (MsEmployee msEmployee : existingEmployee){
            msEmployee.setDeletedAt(Instant.now());
        }

        msEmployeeRepository.saveAll(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        final MsEmployee existingEmployee = msEmployeeRepository.findByDeletedAtIsNullAndId(id)
                .orElseThrow(() -> new RuntimeException("The id was not found"));

        //final List<MsEmployee> Employee = msEmployeeRepository.findAllByDeletedAtIsNull();


        msEmployeeRepository.save(
                existingEmployee.setDeletedAt(Instant.now())
        );

    //return buildData(existingEmployee);
    }

    public List<EmployeeDTO> getAllEmployee() {
        final List<MsEmployee> allEmployee = msEmployeeRepository.findAllByDeletedAtIsNull();

        return allEmployee.stream().map(this::buildData).collect(Collectors.toList());
    }

    private EmployeeDTO buildData(MsEmployee msEmployee) {
        return new EmployeeDTO()
                .setId(msEmployee.getId())
                .setCreatedAt(msEmployee.getCreatedAt().toString())
                .setUpdatedAt(msEmployee.getUpdatedAt().toString())
                //.setDeletedAt(!ObjectUtils.isEmpty(msEmployee.getDeletedAt()) ? msEmployee.getDeletedAt().toString() : "")
                //.setDeletedAt(msEmployee.getDeletedAt().toString())
                .setName(msEmployee.getName())
                .setSalary(msEmployee.getSalary())
                .setGrade(msEmployee.getGrade())
                .setSalaryWithBonus(Grade.calculateSalaryWithBonusByGrade(msEmployee.getGrade(), msEmployee.getSalary()));
    }
}
