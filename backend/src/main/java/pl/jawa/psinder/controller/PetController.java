package pl.jawa.psinder.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    //get all pets from petRepository
    @GetMapping("/pets")
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    //get pet
    @GetMapping("/pets/{id}")
    public Optional<Pet> getPetById(@PathVariable long id) {
        return petRepository.findById(id);
    }

    //create pet
    @PostMapping("/pets")
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    //update pet
    @PutMapping("/pets/{id}")
    public Pet UpdatePet(@PathVariable long id, @RequestBody Pet pet) {
        pet.setId(id);
        return petRepository.save(pet);
    }

    //delete pet with given id
    @DeleteMapping("/pets/{id}")
    public void deletePet(@PathVariable long id) {
        petRepository.deleteById(id);
    }

//    //get pet with filters
//    @GetMapping("/pets")
//    public getFilteredPets(@Param()) {
//
//    }
    //get all pets from user given by id
    //should be in user
    @GetMapping("/users/{id}/pets")
    public List<Pet> getPetsByUserId(@PathVariable long id) {
        return petRepository.findByUserId(id);
    }

    //get user info from pet id
    @GetMapping("/pets/{id}/owner")
    public User getPetParentById(@PathVariable long id) {
        return petRepository.getById(id).getUser();
    }

}
