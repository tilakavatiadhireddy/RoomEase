package com.roomease;

import com.roomease.base.BaseTest;
import com.roomease.pages.RegisterPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test
    public void verifyUserCanRegister() {

        String email =
                "user"
                        + System.currentTimeMillis()
                        + "@test.com";

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "Test User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage
                        .getToastMessage()
                        .contains(
                                "Registration successful"
                        ),
                "Registration should succeed"
        );
    }

    @Test
    public void verifyDuplicateRegistrationFails() {

        String email =
                "duplicate"
                        + System.currentTimeMillis()
                        + "@test.com";

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "Duplicate User",
                email,
                "password123"
        );

        registerPage.getToastMessage();

        registerPage.open();

        registerPage.register(
                "Duplicate User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage
                        .getToastMessage()
                        .contains(
                                "User already exists"
                        ),
                "Duplicate user should not register"
        );
    }
}