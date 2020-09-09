package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetService petService;

    public List<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepository.getScheduleForPet(petId);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepository.getScheduleForEmployee(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Pet> pets = petService.getPetsByOwner(customerId);
        return scheduleRepository.getScheduleForCustomer(pets);
    }

    public Schedule createSchedule(Schedule schedule) {
        Schedule scheduleToSave;
        if(schedule.getScheduleId() != null){
            Optional<Schedule> existingSchedule = scheduleRepository.findById(schedule.getScheduleId());
            if(existingSchedule.isPresent()){
                scheduleToSave = existingSchedule.get();
                scheduleToSave.setActivities(schedule.getActivities());
                scheduleToSave.setDate(schedule.getDate());
                scheduleToSave.setEmployeeIds(schedule.getEmployeeIds());
                scheduleToSave.setPetIds(schedule.getPetIds());
            }else{
                scheduleToSave = schedule;
            }
        }else {
            scheduleToSave = schedule;
        }
        Schedule savedSchedule = scheduleRepository.saveAndFlush(scheduleToSave);
        return savedSchedule;
    }
}
