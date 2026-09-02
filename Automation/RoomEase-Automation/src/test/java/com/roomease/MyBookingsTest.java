package com.roomease;

import com.roomease.base.BaseTest;
import com.roomease.pages.LoginPage;
import com.roomease.pages.RegisterPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class MyBookingsTest extends BaseTest {

    @Test
    public void verifyUserCanOpenMyBookingsPage() {

        // =========================
        // CREATE UNIQUE USER
        // =========================

        String email =
                "mybooking"
                        + System.currentTimeMillis()
                        + "@test.com";

        System.out.println(
                "REGISTERING USER: " + email
        );

        // =========================
        // REGISTER
        // =========================

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "My Booking User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage.isRegistrationSuccessful(),
                "Registration should be successful"
        );

        System.out.println(
                "REGISTRATION SUCCESSFUL"
        );

        // =========================
        // LOGIN
        // =========================

        LoginPage loginPage =
                new LoginPage();

        loginPage.open();

        loginPage.login(
                email,
                "password123"
        );

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Login should be successful"
        );

        System.out.println(
                "LOGIN SUCCESSFUL"
        );

        // =========================
        // WAIT FOR LOGIN TO COMPLETE
        // =========================

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(15)
                );

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.tagName("body")
                )
        );

        // =========================
        // OPEN MY BOOKINGS DIRECTLY
        // =========================

        driver.get(
                "http://localhost:5173/my-bookings"
        );

        System.out.println(
                "NAVIGATED TO MY BOOKINGS"
        );

        // =========================
        // VERIFY URL
        // =========================

        wait.until(
                ExpectedConditions.urlContains(
                        "/my-bookings"
                )
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains(
                        "/my-bookings"
                ),
                "User should navigate to My Bookings page"
        );

        System.out.println(
                "MY BOOKINGS URL VERIFIED: "
                        + driver.getCurrentUrl()
        );

        // =========================
        // VERIFY PAGE LOADED
        // =========================

        WebElement body =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.tagName("body")
                        )
                );

        Assert.assertTrue(
                body.isDisplayed(),
                "My Bookings page should load"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "MY BOOKINGS PAGE TEST PASSED"
        );

        System.out.println(
                "================================"
        );
    }
}