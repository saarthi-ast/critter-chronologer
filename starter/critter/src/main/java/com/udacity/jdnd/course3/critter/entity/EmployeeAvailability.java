package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class EmployeeAvailability {
    private Long employeeId;
    private String days_Available;
}
