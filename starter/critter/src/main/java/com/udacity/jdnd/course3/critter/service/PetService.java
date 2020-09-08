package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.PET_NOT_FOUND_ID;

@Service
public class PetService {
    @Autowired
    private PetRepository repository;

    public Pet savePet(Pet pet) throws PetNotFoundException {
        Pet petToSave;
        if (pet.getPetId() != null) {
            Optional<Pet> petExists = repository.findById(pet.getPetId());
            if (petExists.isPresent()) {
                petToSave = petExists.get();
                petToSave.setBirthDate(pet.getBirthDate());
                petToSave.setName(pet.getName());
                petToSave.setNotes(pet.getNotes());
                petToSave.setOwner(pet.getOwner());
                petToSave.setType(pet.getType());
            }else {
                throw new PetNotFoundException(PET_NOT_FOUND_ID);
            }
        }else{
            petToSave = pet;
        }
        return repository.save(pet);
    }

    public Pet findPetById(Long petId) throws PetNotFoundException{
        Optional<Pet> petExists = repository.findById(petId);
        if(petExists.isPresent()){
            return petExists.get();
        }else {
            throw new PetNotFoundException(PET_NOT_FOUND_ID);
        }
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return repository.findAllByOwnerId(ownerId);
    }
}
