package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.CustomArgsProvider;
import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

//@Tag("models")
class OwnerTest implements ModelTests {
    @Test
    void dependentAssertions() {
        Owner owner = new Owner(1l, "Joe", "Buck");
        owner.setCity("Key West");
        owner.setTelephone("1231231234");

        assertAll("Properties Test",
            () -> assertAll("Person Properties",
                    //() -> assertEquals("Joee", owner.getFirstName(), "First Name Did not Match"),
                    () -> assertEquals("Joe", owner.getFirstName(), "First Name Did not Match"),
                    () -> assertEquals("Buck", owner.getLastName())
                ),
            () -> assertAll("Owner Properties",
//                () -> assertEquals("Key Westt", owner.getCity(), "City Did not Match"),
                () -> assertEquals("Key West", owner.getCity(), "City Did not Match"),
                () -> assertEquals("1231231234", owner.getTelephone())
            )
        );

        assertThat(owner.getCity(), is("Key West"));    // hamcrest
    }

    @DisplayName("Value Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Framework", "Guru"})
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("Enum Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void testEnum(OwnerType ownerType) {
        System.out.println(ownerType);
    }

    @DisplayName("CSV Input Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 3",
    })
    void csvInputTest(String stateName, int val1, int val2) {
        System.out.println(stateName + ", " + val1+ ", " + val2);
    }

    @DisplayName("CSV Input File Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvInputFileTest(String stateName, int val1, int val2) {
        System.out.println(stateName + ", " + val1+ ", " + val2);
    }

    @DisplayName("Method Provider Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @MethodSource("getArgs")
    void methodProviderTest(String stateName, int val1, int val2) {
        System.out.println(stateName + ", " + val1+ ", " + val2);
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of("FL", 3, 1),
                Arguments.of("OH", 4, 2),
                Arguments.of("MI", 5, 3)
        );
    }

    @DisplayName("Custom Provider Source Test")
    @ParameterizedTest(name = "{displayName} :: [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void customProviderTest(String stateName, int val1, int val2) {
        System.out.println(stateName + ", " + val1+ ", " + val2);
    }
}