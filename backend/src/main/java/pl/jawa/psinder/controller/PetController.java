package pl.jawa.psinder.controller;

import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    //get all pets from petRepository
    @GetMapping("")
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    //get all pets from user given by id
    @GetMapping("/user")
    public List<Pet> getPetsByUserId(@RequestParam("id") long id) {
        return petRepository.findByUserId(id);
    }

    //get pet by given id
    @GetMapping("/{id}")
    public Optional<Pet> getPetById(@PathVariable long id) {
        return petRepository.findById(id);
    }

    //get user info from pet id
    @GetMapping("/users")
    public List<String> getPetParentById(@RequestParam("pet_id") long id) {
        List<String> result = new ArrayList<>();
        User temp = petRepository.getById(id).getUser();
        result.add(temp.getUsername());
        result.add(String.valueOf(temp.getId()));
        return result;
    }
}
