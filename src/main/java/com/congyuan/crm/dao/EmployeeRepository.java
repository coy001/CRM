package com.congyuan.crm.dao;

import com.congyuan.crm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //custom implementation
    public List<Employee> findDistinctByFirstNameOrLastName(String firstName, String lastName);

    public List<Employee> findAllByOrderByFirstNameAsc();
    public List<Employee> findAllByOrderByLastNameAsc();
    public List<Employee> findAllByOrderByEmailAsc();


}
