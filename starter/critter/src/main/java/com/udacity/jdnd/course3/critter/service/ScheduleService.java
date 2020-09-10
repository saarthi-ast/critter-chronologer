package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.PET_NOT_FOUND_OWNERID;
import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.SCHEDULE_NOT_FOUND;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetService petService;

    public Set<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepository.getScheduleForPet(petId);
    }

    public Set<Schedule> getAllSchedules() throws ScheduleNotFoundException {
        List<Schedule> list = scheduleRepository.findAll();
        if(!CollectionUtils.isEmpty(list)){
            return new HashSet<>(list);
        }
        throw new ScheduleNotFoundException(SCHEDULE_NOT_FOUND);
    }

    public Set<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepository.getScheduleForEmployee(employeeId);
    }

    public Set<Schedule> getScheduleForCustomer(Long customerId) throws PetNotFoundException {
        List<Pet> pets = petService.getPetsByOwner(customerId);
        if(!CollectionUtils.isEmpty(pets)){
            return scheduleRepository.getScheduleForCustomer(pets.stream().map(Pet::getPetId).collect(Collectors.toList()));
        }
        throw new PetNotFoundException(PET_NOT_FOUND_OWNERID);
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
        return scheduleRepository.save(scheduleToSave);
    }
}
