package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    //we don't add @Autowired because it is optional if we have only one constructor
    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // add mapping for "/list"

    @GetMapping("/list")
    public String listEmployees(Model theModel) {
        // get the employees from db
        List<Employee> employees = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", employees);

        return "employees/list-employees";
    }

    @GetMapping("showFormForAdd")
    public String showFormForAdd(Model theModel) {
        //create model attribute to bind form data
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @GetMapping("showFormForUpdate")
    public String showFormFOrUpdate(@RequestParam("employeeId") int id, Model theModel) {
        // get the employee from the service
        Employee theEmployee = employeeService.findById(id);

        // set employee in the model to prepoulate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to pour form
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int id){

        //delete the employee
        employeeService.deleteById(id);

        //redirect to the employees list
        return "redirect:/employees/list";
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {

        //save the employee
        employeeService.save(employee);
        //use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }


}









