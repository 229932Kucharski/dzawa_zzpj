package pl.jawa.psinder.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.repository.PetRepository;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping("")
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @GetMapping("?userid={id}")
    public List<Pet> getPetsByUserId(@PathVariable long id) {
        return petRepository.findByUserId(id);
    }


}
