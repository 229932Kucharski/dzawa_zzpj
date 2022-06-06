package pl.jawa.psinder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.PetRepository;
import pl.jawa.psinder.webclient.AddressDistanceClient;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public List<Pet> getPets() {
        List<Pet> pets = petRepository.findAll();
        Collections.shuffle(pets);
        return pets;
    }

    public Optional<Pet> getPet(long id) {
        return petRepository.findById(id);
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletePet(long id) {
        petRepository.deleteById(id);
    }

    public List<Pet> getFilteredPets(String race, List<String> sizes, String city, String street, Double distance) {
        List<Pet> result = getPets();
        if (race != null) {
            result = result.stream().filter(pet -> pet.getRace().toLowerCase(Locale.ROOT).contains(race.toLowerCase(Locale.ROOT))).toList();
        }
        if (sizes != null) {
            result = result.stream().filter(pet -> {
                String petSize = pet.getSize().toLowerCase(Locale.ROOT);
                return sizes.contains(petSize);
            }).toList();
        }
        if (city !=null) {
            if (street !=null) {
                String address = city + ", " + street;
                AddressDistanceClient client = new AddressDistanceClient();
                result = result.stream().filter(pet ->
                {
                    String tmp = pet.getAddress().getCity()
                            + ", " + pet.getAddress().getStreet();
                    try {
                        double dist = client.getDistance(address, tmp);
                        if (dist == -1.0) {
                            return false;
                        }
                        return dist <= distance;
                    } catch (JsonProcessingException e) {
                        return false;
                    }
                }).collect(Collectors.toList());
            } else {
                result = result.stream().filter(pet -> pet.getAddress().getCity().toLowerCase(Locale.ROOT).contains(city.toLowerCase(Locale.ROOT))).toList();
            }
        }
        return result;
    }

    public List<Pet> getUsersPets(long id) {
        return petRepository.findByUserId(id);
    }

    public User getOwner(long id) {
        return petRepository.getById(id).getUser();
    }
}
