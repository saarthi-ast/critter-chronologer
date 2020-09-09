package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exception.UserNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.constants.ApplicationConstants.PET_NOT_FOUND_ID;

@Service
public class PetService {
    @Autowired
    private PetRepository repository;

    @Autowired
    private UserService userService;

    public Pet savePet(Pet pet) throws PetNotFoundException, UserNotFoundException {
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
        Pet savedPet = repository.save(petToSave);
        if(savedPet.getOwner() != null){
            Customer customer = savedPet.getOwner();
            if(customer.getPets()!=null){
                customer.getPets().add(savedPet);
                customer.setPets(customer.getPets());
            }else{
                List<Pet> pets = new ArrayList<>();
                pets.add(savedPet);
                customer.setPets(pets);
            }
            userService.saveCustomer(customer);
        }
        return savedPet;
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

    public List<Pet> getPetsByIds(Set<Long> petIds) {
        return repository.findAllByPetIdIn(petIds);
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return repository.findAllByOwnerId(ownerId);
    }
}
