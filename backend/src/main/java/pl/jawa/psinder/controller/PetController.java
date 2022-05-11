package pl.jawa.psinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.PetDto;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.PetAddress;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    //get all pets from petRepository
    @GetMapping("/pets")
    public List<Pet> getPets() {
        return petService.getPets();
    }

    //get pet
    @GetMapping("/pets/{id}")
    public Optional<Pet> getPetById(@PathVariable long id) {
        return petService.getPet(id);
    }

    //create pet
    @PostMapping("/pets")
    public Pet createPet(@RequestBody PetDto petDto) {
        return petService.addPet(new Pet(
                null,
                petService.getOwner(1), //needs to change
                petDto.getName(),
                petDto.getRace(),
                petDto.getSize(),
                petDto.getDescription(),
                null, //needs to change
                petService.getPet(1).get().getAddress() //needs to change
        ));
    }

    //update pet
    @PutMapping("/pets/{id}")
    public Pet updatePet(@PathVariable long id, @RequestBody PetDto petDto) {
        return petService.updatePet(new Pet(
                id,
                petService.getOwner(id),
                petDto.getName(),
                petDto.getRace(),
                petDto.getSize(),
                petDto.getDescription(),
                petService.getPet(id).get().getAvailabilities(),
                petService.getPet(id).get().getAddress()
        ));
    }

    //delete pet with given id
    @DeleteMapping("/pets/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }

    //get pet with filters
    @GetMapping("/pets/filtered")
    public List<Pet> getFilteredPets(@Param("race") String race,
                                     @Param("size") String size,
                                     @Param("city") String city,
                                     @Param("street") String street,
                                     @Param("distance") double distance) {
        return petService.getFilteredPets(race, size, city, street, distance);
    }

    //get all pets from user given by id
    //should be in user
    @GetMapping("/users/{id}/pets")
    public List<Pet> getPetsByUserId(@PathVariable long id) {
        return petService.getUsersPets(id);
    }

    //get user info from pet id
    //CHANE TO userDto
    @GetMapping("/pets/{id}/owner")
    public User getPetParentById(@PathVariable long id) {
        return petService.getOwner(id);
    }

}
