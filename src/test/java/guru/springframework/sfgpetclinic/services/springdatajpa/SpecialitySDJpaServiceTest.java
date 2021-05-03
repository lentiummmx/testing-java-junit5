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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
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
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(1L); //timeout
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
        then(specialtyRepository).should(timeout(100).times(1)).findById(anyLong());    //timeout
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDoThrow() {
        doThrow(RuntimeException.class).when(specialtyRepository).delete(any(Speciality.class));
        
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));
        
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void testFindByIdThrows() {
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("Boom"));

        assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(1L));

        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("BOOM")).given(specialtyRepository).delete(any(Speciality.class));

        assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));

        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(arg -> arg.getDescription().equals(MATCH_ME))))
                .willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = specialitySDJpaService.save(speciality);

        //then
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNoMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("NOT_MATCH_ME");

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(arg -> arg.getDescription().equals(MATCH_ME))))
                .willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = specialitySDJpaService.save(speciality);

        //then
        assertNull(returnedSpeciality);
    }
}