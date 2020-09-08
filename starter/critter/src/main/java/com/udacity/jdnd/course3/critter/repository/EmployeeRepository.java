package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("Select e FROM Employee e, EmployeeSkills s, EmployeeAvailability d " +
            "where e.id=d.employeeId and e.id=s.employeeId and d.daysAvailable = :requestedDay " +
            "and s.skills in :skills")
    List<Employee> findAllByDaysAvailableAndSkillsIn(DayOfWeek requestedDay, Set<EmployeeSkill> skills);
}
