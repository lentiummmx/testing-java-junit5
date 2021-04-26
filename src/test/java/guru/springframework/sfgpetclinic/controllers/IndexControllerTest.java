package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.ValueNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}