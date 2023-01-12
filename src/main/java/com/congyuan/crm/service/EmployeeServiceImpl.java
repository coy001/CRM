package com.congyuan.crm.service;

import com.congyuan.crm.dao.EmployeeRepository;
import com.congyuan.crm.entity.Employee;
import com.congyuan.crm.util.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> search(String keyword) {
        if(keyword == null || keyword.isEmpty()){
            return findAll();
        }
        return employeeRepository.findDistinctByFirstNameOrLastName(keyword, keyword);
    }

    @Override
    public List<Employee> findAllSortBy(int sort) {
        switch (sort){
            case SortParams.FIRST_NAME:
                return employeeRepository.findAllByOrderByFirstNameAsc();
            case SortParams.LAST_NAME:
                return employeeRepository.findAllByOrderByLastNameAsc();
            case SortParams.EMAIL:
                return employeeRepository.findAllByOrderByEmailAsc();
        }
        return findAll();
    }
}
