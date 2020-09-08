package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;
    private String name;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<EmployeeSkill> skills;
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "employee_availability", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;
}
