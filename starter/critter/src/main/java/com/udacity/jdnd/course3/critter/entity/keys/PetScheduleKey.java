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
public class PetScheduleKey implements Serializable {
    @NotNull
    private Long scheduleId;
    @NotNull
    private Long petIds;
}
