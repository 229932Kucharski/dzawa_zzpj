package pl.jawa.psinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.PetRepository;
import pl.jawa.psinder.webclient.AddressDistanceClient;

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
    public Pet getPetById(@PathVariable long id) {
        return petRepository.getById(id);
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

    //get pet with filters
    @GetMapping("/pets/filtered")
    public List<Pet> getFilteredPets(@Param("race") String race, @Param("size") String size, @Param("city") String city, @Param("street") String street, @Param("distance") double distance) {
        List<Pet> result = petRepository.findAll();
        if (race != null) {
            result = result.stream().filter(pet -> pet.getRace().equals(race)).toList();
        }
        if (size != null) {
            result = result.stream().filter(pet -> pet.getSize().equals(size)).toList();
        }
        if (city !=null) {
            if (street !=null) {
                String address = street + ", " + city;
                AddressDistanceClient client = new AddressDistanceClient();
                result = result.stream().filter(pet ->
                        {
                            String tmp = pet.getAddress().getStreet()
                                    + ", " + pet.getAddress().getCity();
                            try {
                                double dist = client.getDistance(address, tmp);
                                return dist <= distance;
                            } catch (JsonProcessingException e) {
                                return false;
                            }
                        }).toList();
            } else {
                result = result.stream().filter(pet -> pet.getAddress().getCity().equals(city)).toList();
            }
        }
        return result;
    }

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
