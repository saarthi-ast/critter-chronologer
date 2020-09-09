package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.keys.ScheduleActivitiesKey;
import com.udacity.jdnd.course3.critter.entity.keys.SkillsKey;
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
@Table(name = "Schedule_Activities")
public class ScheduleActivities {
    @EmbeddedId
    private ScheduleActivitiesKey scheduleActivitiesKey;
}
