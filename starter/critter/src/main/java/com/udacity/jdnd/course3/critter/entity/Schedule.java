package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;

    @ElementCollection(targetClass = Long.class)
    @CollectionTable(name = "employee_schedules", joinColumns = @JoinColumn(name = "scheduleId"))
    private List<Long> employeeIds;

    @ElementCollection(targetClass = Long.class)
    @CollectionTable(name = "pet_schedules", joinColumns = @JoinColumn(name = "scheduleId"))
    private List<Long> petIds;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name = "schedule_activities", joinColumns = @JoinColumn(name = "scheduleId"))
    private Set<EmployeeSkill> activities;
}
