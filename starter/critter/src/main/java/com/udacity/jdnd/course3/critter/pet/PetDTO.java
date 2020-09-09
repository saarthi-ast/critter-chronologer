package com.udacity.jdnd.course3.critter.pet;

import lombok.Data;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class PetDTO {
    private Long petId;
    private PetType type;
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;
}
