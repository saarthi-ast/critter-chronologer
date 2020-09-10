package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.keys.EmployeeScheduleKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_schedules")
public class EmployeeSchedules {
    @EmbeddedId
    private EmployeeScheduleKey employeeScheduleKey;
}
