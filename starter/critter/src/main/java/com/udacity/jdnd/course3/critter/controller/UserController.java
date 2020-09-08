package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.UserNotFoundException;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.*;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            Customer customer = userService.saveCustomer(convertCustomerDTOToEntity(customerDTO));
            return convertCustomerEntityToDTO(customer);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, USER_NOT_FOUND_ID, ex);
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        return customers.stream().map(x->convertCustomerEntityToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId){
        Customer customer = userService.getOwnerByPet(petId);
        return convertCustomerEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = userService.saveEmployee(convertEmployeeDTOToEntity(employeeDTO));
            return convertEmployeeEntityToDTO(employee);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, USER_NOT_FOUND_ID, ex);
        }
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
        try {
            Employee employee = userService.getEmployee(employeeId);
            return convertEmployeeEntityToDTO(employee);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, USER_NOT_FOUND_ID, ex);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable Long employeeId) {
        try {
            userService.setAvailability(daysAvailable,employeeId);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, USER_NOT_FOUND_ID, ex);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = userService.findEmployeesForService(employeeDTO.getDate(),employeeDTO.getSkills());
        return employees.stream().map(x->convertEmployeeEntityToDTO(x)).collect(Collectors.toList());
    }

    private static Customer convertCustomerDTOToEntity(CustomerDTO dto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto,customer);
        return customer;
    }

    private static Employee convertEmployeeDTOToEntity(EmployeeDTO dto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto,employee);
        return employee;
    }

    private static EmployeeDTO convertEmployeeEntityToDTO(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee,dto);
        return dto;
    }

    private static CustomerDTO convertCustomerEntityToDTO(Customer customer){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer,dto);
        return dto;
    }

}
