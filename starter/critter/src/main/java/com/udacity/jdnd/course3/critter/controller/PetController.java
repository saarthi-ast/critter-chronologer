package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.PET_NOT_FOUND_ID;
import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.PET_NOT_FOUND_OWNERID;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
       try {
           Pet pet = petService.savePet(convertPetDTOtoEntity(petDTO));
           return convertPetEntityToDTO(pet);
       }catch (PetNotFoundException ex){
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, PET_NOT_FOUND_ID, ex);
       }

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        try{
            Pet pet = petService.findPetById(petId);
            return convertPetEntityToDTO(pet);
        }catch (PetNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, PET_NOT_FOUND_ID, ex);
        }

    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(x -> convertPetEntityToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return pets.stream().map(x -> convertPetEntityToDTO(x)).collect(Collectors.toList());
    }

    private static Pet convertPetDTOtoEntity(PetDTO dto){
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto,pet);
        return pet;
    }

    private static PetDTO convertPetEntityToDTO(Pet pet){
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet,dto);
        return dto;
    }
}
