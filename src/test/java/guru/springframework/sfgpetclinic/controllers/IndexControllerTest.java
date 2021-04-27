package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.ValueNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("indexd", controller.index(), "Wrong View Returned");
    }

    @Disabled
    @Test
    @DisplayName("Test Exception")
    void oupsHandler() {
        assertTrue("notimplemented".equals(controller.oupsHandler()));
        assertTrue("qwerty".equals(controller.oupsHandler()), () -> "This is some expensive " +
                "Message to build " +
                "for my test");
    }

    @Test
    @DisplayName("Test Exception")
    void oupsHandlerException() {
        assertThrows(ValueNotFoundException.class, () -> controller.oupsHandler());
    }

    @Test
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("I got here");
        });
    }

    @Test
    void testTimeOutPrempt() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("I got here prempt");
        });
    }

    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equals(System.getenv("GURU_RUNTIME")));
    }

    @Test
    void testAssumptionTrueAssumptionIsTrue() {
        assumeTrue("GURU".equals("GURU"));
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testMeOnMacOS() {

    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testMeOnWindows() {

    }

    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void testMeOnJava8() {

    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testMeOnJava11() {

    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "jose.morales")
    @Test
    void testIfUserJM() {

    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "fred")
    @Test
    void testIfUserFred() {

    }
}