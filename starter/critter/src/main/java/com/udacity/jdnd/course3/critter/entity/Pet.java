package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long petId;
    private PetType type;
    private String name;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer owner;
    private LocalDate birthDate;
    private String notes;
}
