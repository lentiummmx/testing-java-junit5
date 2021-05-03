package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    //@Mock
    //PetService petService;

    @Spy
    PetMapService petMapService;

    @InjectMocks
    VisitController visitController;

    @Disabled
    @Test
    void loadPetWithVisit() {
        //given
        Map <String, Object> mapModel = new HashMap<>();
        //given(petService.findById(anyLong())).willReturn(new Pet(1L));

        //when
        Visit visit = visitController.loadPetWithVisit(1L, mapModel);

        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
    }

    @Test
    void loadPetWithVisitWithSpies() {
        //given
        Map <String, Object> mapModel = new HashMap<>();
        Pet pet = new Pet(1L);
        petMapService.save(pet);

        given(petMapService.findById(anyLong())).willCallRealMethod(); //.willReturn(pet);

        //when
        Visit visit = visitController.loadPetWithVisit(1L, mapModel);

        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petMapService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        //given
        Map <String, Object> mapModel = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);
        petMapService.save(pet);
        petMapService.save(pet3);

        given(petMapService.findById(anyLong())).willReturn(pet3);

        //when
        Visit visit = visitController.loadPetWithVisit(1L, mapModel);

        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3L);
        verify(petMapService, times(1)).findById(anyLong());
    }
}