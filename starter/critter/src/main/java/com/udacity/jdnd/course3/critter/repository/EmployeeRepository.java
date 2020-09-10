package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("Select e FROM Employee e, EmployeeSkills s, EmployeeAvailability d " +
            "where e.id=d.availabilityKey.employeeId and e.id=s.skillsKey.employeeId and " +
            "d.availabilityKey.daysAvailable = :requestedDay " +
            "and s.skillsKey.skills in :skills")
    Set<Employee> findAllByDaysAvailableAndSkillsIn(String requestedDay, Set<String> skills);
}
