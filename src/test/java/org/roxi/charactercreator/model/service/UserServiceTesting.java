package org.roxi.charactercreator.model.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTesting {
    private final UserService userService = new UserService(null);

    @Test
    public void testValidEmail() {
        assertTrue(userService.isValidEmail("player@mail.com"), "should pass");
        assertTrue(userService.isValidEmail("admin.admin@domain.co.uk"), "should pass");
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(userService.isValidEmail("player.com"), "should fail");
        assertFalse(userService.isValidEmail("player@gmail"), "should fail");
        assertFalse(userService.isValidEmail(""), "should fair");
        assertFalse(userService.isValidEmail(null), "should fail");
    }
}
