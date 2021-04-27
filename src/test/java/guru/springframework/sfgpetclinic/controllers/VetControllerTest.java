package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest {

    VetService vetService;
    SpecialtyService specialtyService;
    VetController controller;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);
        controller = new VetController(vetService);

        Vet vet = new Vet(1l, "FirstName", "LastName", new HashSet<Speciality>(0));
        Vet vet2 = new Vet(2l, "First Name", "Last Name", new HashSet<>(0));

        vetService.save(vet);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        Model model = new ModelMapImpl();
        String view = controller.listVets(model);

        assertAll("Testing VetController",
                () -> assertEquals("vets/index", view),
                () -> {
                    ModelMapImpl modelMap = (ModelMapImpl) model;
                    Set<Vet> vetsAttr = (Set<Vet>) modelMap.getModelMap().get("vets");
                    assertEquals(2, vetsAttr.size());
                }
        );
    }
}