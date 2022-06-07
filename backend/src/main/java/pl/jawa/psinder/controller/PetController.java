package pl.jawa.psinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.PetDto;
import pl.jawa.psinder.dto.UserDto;
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
    public Pet createPet(@RequestBody Pet pet) {
        PetAddress address = pet.getAddress();
        address.setPet(pet);
        return petService.addPet(pet);
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
    @CrossOrigin
    @DeleteMapping("/pets/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }

    //get pet with filters
    @GetMapping("/pets/filtered")
    public List<Pet> getFilteredPets(@RequestParam(value = "race", required = false) String race,
                                     @RequestParam(value = "size", required = false) List<String> size,
                                     @RequestParam(value = "city", required = false) String city,
                                     @RequestParam(value = "street", required = false) String street,
                                     @RequestParam(value = "distance", required = false) Double distance) {
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
    public UserDto getPetParentById(@PathVariable long id) {
        User user = petService.getOwner(id);
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
