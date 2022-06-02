package pl.jawa.psinder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.Pet;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.PetRepository;
import pl.jawa.psinder.webclient.AddressDistanceClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public List<Pet> getPets() {
        return petRepository.findAll();
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

    public List<Pet> getFilteredPets(String race, String size, String city, String street, double distance) {
        List<Pet> result = getPets();
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

    public List<Pet> getUsersPets(long id) {
        return petRepository.findByUserId(id);
    }

    public User getOwner(long id) {
        return petRepository.getById(id).getUser();
    }
}
