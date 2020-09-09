package com.udacity.jdnd.course3.critter.entity.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SkillsKey implements Serializable {
    @NotNull
    private Long employeeId;
    @NotNull
    private String skills;
}
