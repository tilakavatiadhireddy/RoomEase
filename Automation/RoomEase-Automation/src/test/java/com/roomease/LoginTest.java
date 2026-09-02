package com.roomease;

import com.roomease.base.BaseTest;
import com.roomease.pages.LoginPage;
import com.roomease.pages.RegisterPage;
import com.roomease.pages.NavbarPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifyInvalidLogin() {

        LoginPage loginPage =
                new LoginPage();

        loginPage.open();

        loginPage.login(
                "invalid@test.com",
                "wrongpassword"
        );

        Assert.assertTrue(
                loginPage.getToastMessage()
                        .toLowerCase()
                        .contains("invalid"),
                "Invalid login message should appear"
        );
    }


    @Test
    public void verifyUserCanLogin() {

        String email =
                "login"
                        + System.currentTimeMillis()
                        + "@test.com";

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "Login User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage.isRegistrationSuccessful(),
                "User registration should be successful"
        );

        LoginPage loginPage =
                new LoginPage();

        loginPage.open();

        loginPage.login(
                email,
                "password123"
        );

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "User should login successfully"
        );
    }


    @Test
    public void verifyUserCanLogout() {

        String email =
                "logout"
                        + System.currentTimeMillis()
                        + "@test.com";

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "Logout User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage.isRegistrationSuccessful(),
                "User registration should be successful"
        );

        LoginPage loginPage =
                new LoginPage();

        loginPage.open();

        loginPage.login(
                email,
                "password123"
        );

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "User should login successfully before logout"
        );

        NavbarPage navbar =
                new NavbarPage();

        Assert.assertTrue(
                navbar.isLogoutButtonVisible(),
                "Logout button should be visible"
        );

        navbar.logout();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .equals("http://localhost:5173/"),
                "User should return to home after logout"
        );
    }
}