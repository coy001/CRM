package com.congyuan.crm.controller;

import com.congyuan.crm.entity.Employee;
import com.congyuan.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployees(Model model, @RequestParam(required = false) Integer sort){

        List<Employee> employees;

        if (sort == null){
            employees = employeeService.findAll();
        } else{
            employees = employeeService.findAllSortBy(sort);
        }

        model.addAttribute("employees",employees);

        return "list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Employee employee= new Employee();

        model.addAttribute("employee",employee);

        return "employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
        Employee employee= employeeService.findById(id);

        model.addAttribute("employee",employee);

        return "employee-form";
    }

    @GetMapping("/showFormForDelete")
    public String showFormForDelete(@RequestParam("employeeId") int id, Model model) {

        employeeService.deleteById(id);

        return "redirect:/employees/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee employee){
        employeeService.save(employee);
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        List<Employee> employees = employeeService.search(keyword);

        model.addAttribute("employees",employees);
        model.addAttribute("keyword",keyword);

        return "list-employees";
    }
}
