package pl.jawa.psinder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.repository.PetRepository;

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
    @GetMapping("?userid={id}")
    public List<Pet> getPetsByUserId(@PathVariable long id) {
        return petRepository.findByUserId(id);
    }

    @GetMapping("?id={id}")
    public Optional<Pet> getPetById(@PathVariable long id) {
        return petRepository.findById(id);
    }
}
