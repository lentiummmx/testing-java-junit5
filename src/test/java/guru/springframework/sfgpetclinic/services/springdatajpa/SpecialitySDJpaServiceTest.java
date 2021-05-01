package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void deleteById() {
        //given - none

        //when
        specialitySDJpaService.deleteById(1L);

        //then
        //verify(specialtyRepository).deleteById(1L);
        then(specialtyRepository).should(times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdTimes() {
        //when
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //then
        //verify(specialtyRepository, times(2)).deleteById(1L);
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //when
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //then
        //verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        //when
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //then
        //verify(specialtyRepository, atMost(5)).deleteById(1L);
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        //when
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //then
        //verify(specialtyRepository, never()).deleteById(5L);
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void delete() {
        //given
        Speciality speciality = new Speciality();

        //when
        specialitySDJpaService.delete(speciality);

        //then
        //verify(specialtyRepository).delete(any(Speciality.class));
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findById() {
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpecialty = specialitySDJpaService.findById(1L);
        assertThat(foundSpecialty).isNotNull();

        verify(specialtyRepository).findById(1L);
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void findByIdBddTest() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpecialty = specialitySDJpaService.findById(1L);
        assertThat(foundSpecialty).isNotNull();

        //then
        //verify(specialtyRepository).findById(anyLong());
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }
}