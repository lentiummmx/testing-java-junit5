package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest {

    VetService service;

    @BeforeEach
    void setUp() {
        service = new VetService() {
            @Override
            public Set<Vet> findAll() {
                Set<Vet> vetSet = new HashSet<>();
                vetSet.add(new Vet(1l, "FirstName", "LastName", null));
                vetSet.add(new Vet(2l, "First Name", "Last Name", null));
                return vetSet;
            }

            @Override
            public Vet findById(Long aLong) {
                return null;
            }

            @Override
            public Vet save(Vet object) {
                return null;
            }

            @Override
            public void delete(Vet object) {

            }

            @Override
            public void deleteById(Long aLong) {

            }
        };
    }

    @Test
    void listVets() {
        VetController controller = new VetController(service);

        Map<String, Object> modelMap = new HashMap<>();
        Model model = new Model() {
            @Override
            public void addAttribute(String key, Object o) {
                modelMap.put(key, o);
            }

            @Override
            public void addAttribute(Object o) {

            }
        };

        assertAll("Testing VetController",
                () -> assertEquals("vets/index", controller.listVets(model)),
                () -> assertEquals(service.findAll().size(), ((Set<Vet>) modelMap.get("vets")).size())
        );
    }
}