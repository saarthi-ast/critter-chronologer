package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class EmployeeSkills {
    private Long employeeId;
    private String skill;
}
