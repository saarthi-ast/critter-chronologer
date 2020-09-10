package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertScheduleDTOtoEntity(scheduleDTO));
        return convertScheduleEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try{
            Set<Schedule> schedules = scheduleService.getAllSchedules();
            return schedules.stream().map(ScheduleController::convertScheduleEntityToDTO).collect(Collectors.toList());
        }catch (ScheduleNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, SCHEDULE_NOT_FOUND, ex);
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        Set<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream().map(ScheduleController::convertScheduleEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        Set<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream().map(ScheduleController::convertScheduleEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        try{
            Set<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
            return schedules.stream().map(ScheduleController::convertScheduleEntityToDTO).collect(Collectors.toList());
        }catch (PetNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, PET_NOT_FOUND_OWNERID, ex);
        }
    }

    private static Schedule convertScheduleDTOtoEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto, schedule);
        return schedule;
    }

    private static ScheduleDTO convertScheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        if (dto.getPetIds() != null) {
            dto.setPetIds(new ArrayList(dto.getPetIds()));
        }
        if(dto.getEmployeeIds()!= null){
            dto.setEmployeeIds(new ArrayList(dto.getEmployeeIds()));
        }
        return dto;
    }
}
