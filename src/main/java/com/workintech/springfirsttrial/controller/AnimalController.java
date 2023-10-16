package com.workintech.springfirsttrial.controller;

import com.workintech.springfirsttrial.dto.AnimalResponse;
import com.workintech.springfirsttrial.entity.Animal;
import com.workintech.springfirsttrial.validation.AnimalValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private Map<Integer, Animal> animals;

    @PostConstruct
    public void init() {
        System.out.println("Animal Map created");
        animals = new HashMap<>();
    }

    public AnimalController() {
        System.out.println("Animal created");
    }

    @GetMapping("/")
    public List<Animal> findAll() {
        System.out.println("Get all animals");
        return animals.values().stream().toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse find(@PathVariable int id) {
        if (!AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Animal Id is not valid", 400);
        }
        if (!AnimalValidation.isAnimalContains(animals, id)) {
            return new AnimalResponse(null, "Animal with given id is not exist: " + id, 400);
        }
        return new AnimalResponse(animals.get(id).getName(), "Success", 200);
    }

    @PostMapping("/")
    public AnimalResponse save(@RequestBody Animal animal) {
        if (!AnimalValidation.isAnimalCredentialsValid(animal)) {
            return new AnimalResponse(null, "Animal Credentials are not valid", 400);
        }
        animals.put(animal.getId(), animal);
        return new AnimalResponse(animals.get(animal.getId()).getName(), "success", 200);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable int id, @RequestBody Animal animal) {
        animal.setId(id);
        if (!AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Animal Id is not valid", 400);
        }
        if (!AnimalValidation.isAnimalContains(animals, id)) {
            return new AnimalResponse(null, "Animal with given id is not exist: " + id, 400);
        }
        if (!AnimalValidation.isAnimalCredentialsValid(animal)) {
            return new AnimalResponse(null, "Animal Credentials are not valid", 400);
        }

        animals.put(id, new Animal(id, animal.getName(), animal.getWeight()));
        return new AnimalResponse(animals.get(id).getName(), "success", 200);
    }

    @DeleteMapping("/{id}")
    public AnimalResponse remove(@PathVariable int id) {
        if (!AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Animal Id is not valid", 400);
        }
        if (!AnimalValidation.isAnimalContains(animals, id)) {
            return new AnimalResponse(null, "Animal with given id is not exist: " + id, 400);
        }

        return new AnimalResponse(animals.remove(id).getName(), "success", 200);
    }


}
