package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s, PetSchedules p, EmployeeSchedules e, ScheduleActivities sa \n" +
            "where s.id = p.petScheduleKey.scheduleId and \n" +
            " s.id = e.employeeScheduleKey.scheduleId and s.id = sa.scheduleActivitiesKey.scheduleId" +
            " and p.petScheduleKey.petId = :petId")
    List<Schedule> getScheduleForPet(Long petId);

    @Query("SELECT s FROM Schedule s, PetSchedules p, EmployeeSchedules e, ScheduleActivities sa \n" +
            "where s.id = p.petScheduleKey.scheduleId and \n" +
            " s.id = e.employeeScheduleKey.scheduleId and s.id = sa.scheduleActivitiesKey.scheduleId" +
            " and e.employeeScheduleKey.employeeId = :employeeId")
    List<Schedule> getScheduleForEmployee(Long employeeId);

    @Query("SELECT s FROM Schedule s, PetSchedules p, EmployeeSchedules e, ScheduleActivities sa \n" +
            "where s.id = p.petScheduleKey.scheduleId and \n" +
            " s.id = e.employeeScheduleKey.scheduleId and s.id = sa.scheduleActivitiesKey.scheduleId" +
            " and p.petScheduleKey.petId in :pets")
    List<Schedule> getScheduleForCustomer(List<Pet> pets);
}
