package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.UserNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.*;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer) throws UserNotFoundException {
        Customer customerToSave;
        if (customer.getCustomerId() != null) {
            Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerId());
            if (existingCustomer.isPresent()) {
                customerToSave = existingCustomer.get();
                customerToSave.setName(customer.getName());
                customerToSave.setNotes(customer.getNotes());
                customerToSave.setPets(customer.getPets());
                customerToSave.setPhoneNumber(customer.getPhoneNumber());
            } else {
                throw new UserNotFoundException(USER_NOT_FOUND_ID);
            }
        } else {
            customerToSave = customer;
        }
        return customerRepository.save(customerToSave);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId) throws UserNotFoundException {
        Customer customer = customerRepository.findByPetId(petId);
        if (customer != null) {
            return customer;
        }
        throw new UserNotFoundException(USER_NOT_FOUND_PET_ID);
    }

    public Employee saveEmployee(Employee employee) throws UserNotFoundException {
        Employee employeeToSave;
        if (employee.getEmployeeId() != null) {
            Optional<Employee> existingEmployee = employeeRepository.findById(employee.getEmployeeId());
            if (existingEmployee.isPresent()) {
                employeeToSave = existingEmployee.get();
                employeeToSave.setName(employee.getName());
                employeeToSave.setDaysAvailable(employee.getDaysAvailable());
                employeeToSave.setSkills(employee.getSkills());
            } else {
                throw new UserNotFoundException(USER_NOT_FOUND_ID);
            }
        } else {
            employeeToSave = employee;
        }
        return employeeRepository.save(employeeToSave);
    }

    public Employee getEmployee(Long employeeId) throws UserNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            throw new UserNotFoundException(PET_NOT_FOUND_ID);
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) throws UserNotFoundException {
        Employee employeeToSave;
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
        if (existingEmployee.isPresent()) {
            employeeToSave = existingEmployee.get();
            employeeToSave.setDaysAvailable(daysAvailable);
            employeeRepository.save(employeeToSave);
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND_ID);
        }
    }

    public Set<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        DayOfWeek dayRequested = date.getDayOfWeek();
        Set<String> skillsets = skills.stream().map(x -> x.toString()).collect(Collectors.toSet());
        return employeeRepository.findAllByDaysAvailableAndSkillsIn(dayRequested.toString(), skillsets);
    }

    public Customer getCustomerById(Long customerId) throws UserNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new UserNotFoundException(CUSTOMER_NOT_FOUND_ID);
        }
    }

    public void addPetToCustomer(Pet savedPet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            pets.add(savedPet);
        } else {
            pets = new ArrayList<>();
            pets.add(savedPet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }
}
