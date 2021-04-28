package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

@Tag("models")
public interface ModelTests {
    @BeforeEach
    default void setUp(TestInfo testInfo) {
        System.out.println("Running Test - " + testInfo.getDisplayName() + " ::: ");
    }
}
