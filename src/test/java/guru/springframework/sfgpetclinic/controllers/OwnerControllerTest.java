package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.BindingResultImpl;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock(lenient = true)
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult result;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringCaptor.capture()))
                .willAnswer(invocationOnMock -> {
            String lastName = invocationOnMock.getArgument(0);
            List<Owner> owners = new ArrayList<>();

            Owner owner = new Owner(2L, "First Name", "Last Name");
            if (lastName.equals("%Last Name%")) {
                owners.add(owner);
                return owners;
            } else if (lastName.equals("%Do Not Find Me%")) {
                return owners;
            } else if (lastName.equals("%%") || lastName.equals("%Find Many%")) {
                owners.add(owner);
                owners.add(new Owner(3L, "First Name 2", "Last Name 2"));
                return owners;
            }

            throw new RuntimeException("Invalid Argument");
        });
    }

    @Test
    void processCreationForm() {
        //given
        //BindingResult result = new BindingResultImpl();
        Owner owner = new Owner(5L, "First Name", "Last Name");
        given(result.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        //when
        String view = ownerController.processCreationForm(owner, result);
        assertThat(view).isEqualTo(REDIRECT_OWNERS_5);

        //then
        then(ownerService).should().save(any(Owner.class));
    }

    @Test
    void processCreationFormFails() {
        //given
        Owner owner = new Owner(5L, "First Name", "Last Name");
        given(result.hasErrors()).willReturn(true);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        //when
        String view = ownerController.processCreationForm(owner, result);
        assertThat(view).isNotEqualTo(REDIRECT_OWNERS_5);

        //then
        then(ownerService).should(never()).save(any(Owner.class));
    }

    @Disabled
    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(2L, "First Name", "Last Name");
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(owners);

        //when
        String view = ownerController.processFindForm(owner, result, null);
        assertThat(view).isEqualTo("redirect:/owners/2");

        //then
        //then(ownerService).should().findAllByLastNameLike(argThat(arg -> arg.equals("%Last Name%")));
        assertThat("%Last Name%").isEqualToIgnoringCase(captor.getValue());
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(2L, "First Name", "Last Name");
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);
        //given(ownerService.findAllByLastNameLike(stringCaptor.capture())).willReturn(owners);

        //when
        String view = ownerController.processFindForm(owner, result, null);
        assertThat(view).isEqualTo("redirect:/owners/2");

        //then
        //then(ownerService).should().findAllByLastNameLike(argThat(arg -> arg.equals("%Last Name%")));
        assertThat("%Last Name%").isEqualToIgnoringCase(stringCaptor.getValue());
    }

    @Test
    void processFindFormEmptyResult() {
        //given
        Owner owner = new Owner(2L, "Joe", "Do Not Find Me");

        //when
        String view = ownerController.processFindForm(owner, result, null);
        assertThat("owners/findOwners").isEqualToIgnoringCase(view);

        //then
        assertThat("%Do Not Find Me%").isEqualToIgnoringCase(stringCaptor.getValue());
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormOneResult() {
        //given
        Owner owner = new Owner(2L, "Joe", "Last Name");

        //when
        String view = ownerController.processFindForm(owner, result, null);
        assertThat("redirect:/owners/2").isEqualToIgnoringCase(view);

        //then
        assertThat("%Last Name%").isEqualToIgnoringCase(stringCaptor.getValue());
        //verifyZeroInteractions(model);
        verifyNoInteractions(model);
    }

    @Test
    void processFindFormManyResults() {
        //given
        Owner owner = new Owner(2L, "Joe", "Find Many");

        //when
        //String view = ownerController.processFindForm(owner, result, Mockito.mock(Model.class));
        String view = ownerController.processFindForm(owner, result, model);
        assertThat("owners/ownersList").isEqualToIgnoringCase(view);

        //then
        assertThat("%Find Many%").isEqualToIgnoringCase(stringCaptor.getValue());
    }

    @Test
    void processFindFormManyResultsWhenNullLastName() {
        //given
        Owner owner = new Owner(2L, "Joe", null);
        InOrder inOrder = inOrder(ownerService, model);

        //when
        String view = ownerController.processFindForm(owner, result, model);

        //then
        assertThat("owners/ownersList").isEqualToIgnoringCase(view);
        assertThat("%%").isEqualToIgnoringCase(stringCaptor.getValue());

        //inOrder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }
}