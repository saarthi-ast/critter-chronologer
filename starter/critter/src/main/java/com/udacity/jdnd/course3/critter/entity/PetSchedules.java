package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.keys.PetScheduleKey;
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
@Table(name = "pet_schedules")
public class PetSchedules {
    @EmbeddedId
    private PetScheduleKey petScheduleKey;
}
