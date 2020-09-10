package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(x->convertScheduleEntityToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream().map(x->convertScheduleEntityToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        Set<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream().map(x->convertScheduleEntityToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream().map(x->convertScheduleEntityToDTO(x)).collect(Collectors.toList());
    }

    private static Schedule convertScheduleDTOtoEntity(ScheduleDTO dto){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);
        return schedule;
    }

    private static ScheduleDTO convertScheduleEntityToDTO(Schedule schedule){
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,dto);
        return dto;
     }
}
